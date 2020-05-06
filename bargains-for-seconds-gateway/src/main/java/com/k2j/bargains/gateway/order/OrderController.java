package com.k2j.bargains.gateway.order;

import com.k2j.bargains.common.api.goods.GoodsServiceApi;
import com.k2j.bargains.common.api.goods.vo.GoodsVo;
import com.k2j.bargains.common.api.order.OrderServiceApi;
import com.k2j.bargains.common.api.order.vo.OrderDetailVo;
import com.k2j.bargains.common.api.user.vo.UserVo;
import com.k2j.bargains.common.domain.OrderInfo;
import com.k2j.bargains.common.result.CodeMsg;
import com.k2j.bargains.common.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @className: OrderController
 * @description: 订单服务接口
 * @author: Sakura
 * @date: 4/10/20
 **/
@Controller
@RequestMapping("/order/")
public class OrderController {

    @Reference(interfaceClass = OrderServiceApi.class)
    OrderServiceApi orderService;

    @Reference(interfaceClass = GoodsServiceApi.class)
    GoodsServiceApi goodsService;

    /**
     * @description: 获取订单详情
     * @author: Sakura
     * @date: 4/10/20
     * @param model:
     * @param user:
     * @param orderId:
     * @return: com.k2j.bargains.common.result.Result<com.k2j.bargains.common.api.order.vo.OrderDetailVo>
     **/
    @RequestMapping("detail")
    @ResponseBody
    public Result<OrderDetailVo> orderInfo(Model model,
                                           UserVo user,
                                           @RequestParam("orderId") long orderId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 获取订单信息
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        // 如果订单存在，则根据订单信息获取商品信息
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setUser(user);// 设置用户信息
        vo.setOrder(order); // 设置订单信息
        vo.setGoods(goods); // 设置商品信息
        return Result.success(vo);
    }
}
