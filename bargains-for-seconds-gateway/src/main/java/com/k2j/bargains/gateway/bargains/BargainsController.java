package com.k2j.bargains.gateway.bargains;

import com.k2j.bargains.common.api.bargains.BargainsServiceApi;
import com.k2j.bargains.common.api.bargains.vo.VerifyCodeVo;
import com.k2j.bargains.common.api.cache.RedisServiceApi;
import com.k2j.bargains.common.api.cache.vo.BgKeyPrefix;
import com.k2j.bargains.common.api.cache.vo.GoodsKeyPrefix;
import com.k2j.bargains.common.api.cache.vo.OrderKeyPrefix;
import com.k2j.bargains.common.api.goods.GoodsServiceApi;
import com.k2j.bargains.common.api.goods.vo.GoodsVo;
import com.k2j.bargains.common.api.mq.MqProviderApi;
import com.k2j.bargains.common.api.mq.vo.BgMessage;
import com.k2j.bargains.common.api.order.OrderServiceApi;
import com.k2j.bargains.common.api.user.vo.UserVo;
import com.k2j.bargains.common.domain.BargainsOrder;
import com.k2j.bargains.common.limit.annotation.SpringControllerLimit;
import com.k2j.bargains.common.result.CodeMsg;
import com.k2j.bargains.common.result.Result;
import com.k2j.bargains.common.util.MD5Util;
import com.k2j.bargains.common.util.UUIDUtil;
import com.k2j.bargains.common.util.VerifyCodeUtil;
import com.k2j.bargains.gateway.config.interceptor.AccessLimit;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: BargainsController
 * @description: 秒杀接口
 * @author: Sakura
 * @date: 4/10/20
 **/
@Controller
@RequestMapping("/bargains/")
public class BargainsController implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(BargainsController.class);

    @Reference(interfaceClass = RedisServiceApi.class)
    RedisServiceApi redisService;

    @Reference(interfaceClass = GoodsServiceApi.class)
    GoodsServiceApi goodsService;

    @Reference(interfaceClass = BargainsServiceApi.class)
    BargainsServiceApi bargainsService;

    @Reference(interfaceClass = OrderServiceApi.class)
    OrderServiceApi orderService;

    @Reference(interfaceClass = MqProviderApi.class)
    MqProviderApi sender;

    /**
     * @description: 用于内存标记，标记库存是否为空，从而减少对redis的访问
     * @author: Sakura
     * @date: 4/10/20
     **/
    private Map<Long, Boolean> localOverMap = new HashMap<>();

    /**
     * @description: 获取秒杀接口地址
     * 1. 每一次点击秒杀，都会生成一个随机的秒杀地址返回给客户端
     * 2. 对秒杀的次数做限制（通过自定义拦截器注解完成）
     * @author: Sakura
     * @date: 4/10/20
     * @param model:
     * @param user:
     * @param goodsId: 秒杀的商品id
     * @param verifyCode: 验证码
     * @return: com.k2j.bargains.common.result.Result<java.lang.String> 被隐藏的秒杀接口路径
     **/
    @SpringControllerLimit(errorCode = 200)
    @AccessLimit(seconds = 5, maxAccessCount = 5, needLogin = true)
    @RequestMapping(value = "path", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getBargainsPath(Model model, UserVo user,
                                         @RequestParam("goodsId") long goodsId,
                                         @RequestParam(value = "verifyCode", defaultValue = "0") int verifyCode) {

        /** 在执行下面的逻辑之前，会先对path请求进行拦截处理（@AccessLimit， AccessInterceptor），防止访问次数过于频繁，对服务器造成过大的压力 **/

        model.addAttribute("user", user);

        if (goodsId <= 0) {
            return Result.error(CodeMsg.BARGAINS_PARAM_ILLEGAL.fillArgs("商品id小于0"));
        }

        // 校验验证码
        boolean check = this.checkVerifyCode(user, goodsId, verifyCode);

        if (!check)
            return Result.error(CodeMsg.VERIFY_FAIL);// 检验不通过，请求非法

        // 检验通过，获取秒杀路径
        String path = this.createBgPath(user, goodsId);

        // 向客户端回传随机生成的秒杀地址
        return Result.success(path);
    }

    /**
     * @description: 检验检验码的计算结果
     * @author: Sakura
     * @date: 4/10/20
     * @param user:
     * @param goodsId:
     * @param verifyCode:
     * @return: boolean
     **/
    private boolean checkVerifyCode(UserVo user, long goodsId, int verifyCode) {
        if (user == null || goodsId <= 0) {
            return false;
        }

        // 从redis中获取验证码计算结果
        Integer oldCode = redisService.get(BgKeyPrefix.VERIFY_RESULT, user.getUuid() + "_" + goodsId, Integer.class);
        if (oldCode == null || oldCode - verifyCode != 0) {
            return false;
        }

        // 如果校验成功，则说明校验码过期，删除校验码缓存，防止重复提交同一个验证码
        redisService.delete(BgKeyPrefix.VERIFY_RESULT, user.getUuid() + "_" + goodsId);
        return true;
    }

    /**
     * @description: 创建秒杀地址, 并将其存储在redis中
     * @author: Sakura
     * @date: 4/10/20
     * @param user:
     * @param goodsId:
     * @return: java.lang.String
     **/
    public String createBgPath(UserVo user, long goodsId) {

        if (user == null || goodsId <= 0) {
            return null;
        }

        // 随机生成秒杀地址
        String path = MD5Util.md5(UUIDUtil.uuid() + "123456");
        // 将随机生成的秒杀地址存储在redis中（保证不同的用户和不同商品的秒杀地址是不一样的）
        redisService.set(BgKeyPrefix.BG_PATH, "" + user.getUuid() + "_" + goodsId, path);
        return path;
    }

    /**
     * @description: 秒杀逻辑（页面静态化，不需要直接将页面返回给客户端，而是返回客户端需要的页面动态数据，返回数据是json格式）
     * QPS:1306
     * 5000 * 10
     * GET/POST的@RequestMapping是有区别的
     * 通过随机的path，客户端隐藏秒杀接口
     * 优化: 不同于每次都去数据库中读取秒杀订单信息，而是在第一次生成秒杀订单成功后，
     * 将订单存储在redis中，再次读取订单信息的时候就直接从redis中读取
     * @author: Sakura
     * @date: 4/10/20
     * @param model:
     * @param user:
     * @param goodsId:
     * @param path: 隐藏的秒杀地址，为客户端回传的path，最初也是有服务端产生的
     * @return: com.k2j.bargains.common.result.Result<java.lang.Integer> 订单详情或错误码
     **/
    // {path}为客户端回传的path，最初也是由服务端产生的
    @RequestMapping(value = "{path}/doBargains", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> doBargains(Model model, UserVo user,
                                     @RequestParam("goodsId") long goodsId,
                                     @PathVariable("path") String path) {

        model.addAttribute("user", user);

        // 验证path是否正确
        boolean check = this.checkPath(user, goodsId, path);

        if (!check)
            return Result.error(CodeMsg.REQUEST_ILLEGAL);// 请求非法

        // 通过内存标记，减少对redis的访问，秒杀未结束才继续访问redis
        Boolean over = localOverMap.get(goodsId);
        if (over)
            return Result.error(CodeMsg.BARGAINS_OVER);

        // 预减库存，同时在库存为0时标记该商品已经结束秒杀
        Long stock = redisService.decr(GoodsKeyPrefix.GOODS_STOCK, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);// 秒杀结束。标记该商品已经秒杀结束
            return Result.error(CodeMsg.BARGAINS_OVER);
        }

        // 判断是否重复秒杀
        // 从redis中取缓存，减少数据库的访问
        BargainsOrder order = redisService.get(OrderKeyPrefix.BARGAINS_ORDER, ":" + user.getUuid() + "_" + goodsId, BargainsOrder.class);
        // 如果缓存中不存该数据，则从数据库中取
        if (order == null) {
            order = orderService.getBargainsOrderByUserIdAndGoodsId(user.getUuid(), goodsId);
        }

        if (order != null) {
            return Result.error(CodeMsg.REPEAT_BARGAINS);
        }

        // 商品有库存且用户为秒杀商品，则将秒杀请求放入MQ
        BgMessage message = new BgMessage();
        message.setUser(user);
        message.setGoodsId(goodsId);

        // 放入MQ(对秒杀请求异步处理，直接返回)
        sender.sendBgMessage(message);

        // 排队中
        return Result.success(0);
    }

    /**
     * @description: 验证路径是否正确
     * @author: Sakura
     * @date: 4/10/20
     * @param user:
     * @param goodsId:
     * @param path:
     * @return: boolean
     **/
    public boolean checkPath(UserVo user, long goodsId, String path) {
        if (user == null || path == null)
            return false;
        // 从redis中读取出秒杀的path变量是否为本次秒杀操作执行前写入redis中的path
        String oldPath = redisService.get(BgKeyPrefix.BG_PATH, "" + user.getUuid() + "_" + goodsId, String.class);

        return path.equals(oldPath);
    }

    /**
     * @description: 用于返回用户秒杀的结果
     * @author: Sakura
     * @date: 4/10/20
     * @param model:
     * @param user:
     * @param goodsId:
     * @return: com.k2j.bargains.common.result.Result<java.lang.Long> orderId：成功, -1：秒杀失败, 0： 排队中
     **/
    @RequestMapping(value = "result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> getBargainsResult(Model model,
                                         UserVo user,
                                         @RequestParam("goodsId") long goodsId) {

        model.addAttribute("user", user);

        long result = bargainsService.getBargainsResult(user.getUuid(), goodsId);
        return Result.success(result);
    }

    /**
     * @description: goods_detail.htm: $("#verifyCodeImg").attr("src", "/bargains/verifyCode?goodsId=" + $("#goodsId").val());
     * 使用HttpServletResponse的输出流返回客户端异步获取的验证码（异步获取的代码如上所示）
     * @author: Sakura
     * @date: 4/10/20
     * @param response:
     * @param user:
     * @param goodsId:
     * @return: com.k2j.bargains.common.result.Result<java.lang.String>
     **/
    @RequestMapping(value = "verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getVerifyCode(HttpServletResponse response, UserVo user,
                                        @RequestParam("goodsId") long goodsId) {
        logger.info("获取验证码");
        if (user == null || goodsId <= 0) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 刷新验证码的时候置缓存中的随机地址无效
        String path = redisService.get(BgKeyPrefix.BG_PATH, "" + user.getUuid() + "_" + goodsId, String.class);
        if (path != null)
            redisService.delete(BgKeyPrefix.BG_PATH, "" + user.getUuid() + "_" + goodsId);

        // 创建验证码
        try {
            VerifyCodeVo verifyCode = VerifyCodeUtil.createVerifyCode();

            // 验证码结果预先存到redis中
            redisService.set(BgKeyPrefix.VERIFY_RESULT, user.getUuid() + "_" + goodsId, verifyCode.getExpResult());
            ServletOutputStream out = response.getOutputStream();
            // 将图片写入到resp对象中
            ImageIO.write(verifyCode.getImage(), "JPEG", out);
            out.close();
            out.flush();

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.BARGAINS_FAIL);
        }
    }

    /**
     * @description: 服务器程序启动的时候加载商品列表信息
     * @author: Sakura
     * @date: 4/10/20
     * @return: void
     **/
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goods = goodsService.listGoodsVo();
        if (goods == null) {
            return;
        }

        // 将商品的库存信息存储在redis中
        for (GoodsVo good : goods) {
            redisService.set(GoodsKeyPrefix.GOODS_STOCK, "" + good.getId(), good.getStockCount());
            // 在系统启动时，标记库存不为空
            localOverMap.put(good.getId(), false);
        }
    }
}
