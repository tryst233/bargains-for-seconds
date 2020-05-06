package com.k2j.bargains.gateway.user;

import com.k2j.bargains.common.api.cache.vo.BgUserKeyPrefix;
import com.k2j.bargains.common.api.user.UserServiceApi;
import com.k2j.bargains.common.api.user.vo.LoginVo;
import com.k2j.bargains.common.api.user.vo.RegisterVo;
import com.k2j.bargains.common.result.CodeMsg;
import com.k2j.bargains.common.result.Result;
import com.k2j.bargains.gateway.exception.GlobalException;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @className: UserController
 * @description: 用户接口
 * @author: Sakura
 * @date: 4/9/20
 **/
@Controller
@RequestMapping("/user/")
public class UserController {

    /**
     * @description: 日志记录：Logger是由slf4j接口规范创建的，对象有具体的实现类创建
     * @author: Sakura
     * @date: 4/9/20
     * @param null:
     * @return: null
     **/
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Reference(interfaceClass = UserServiceApi.class)
    UserServiceApi userService;

    /**
     * @description: 首页
     * @author: Sakura
     * @date: 4/9/20
     * @return: java.lang.String
     **/
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        logger.info("首页接口");
        return "login";// login页面
    }

    /**
     * @description: 用户登录接口
     * @author: Sakura
     * @date: 4/9/20
     * @param response: 响应
     * @param loginVo: 用户登录请求的表单数据（将表单数据封装为了一个Vo：Value Object）
     *                 注解@Valid用于校验表单参数，校验成功才会继续执行业务逻辑，否则，
     *                 请求参数校验不成功抛出异常
     * @return: com.k2j.bargains.common.result.Result<java.lang.Boolean>
     **/
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> login(HttpServletResponse response, @Valid LoginVo loginVo) {

        String token = userService.login(loginVo);
        logger.info("token: " + token);

        // 将token写入cookie中, 然后传给客户端（一个cookie对应一个用户，这里将这个cookie的用户信息写入redis中）
        Cookie cookie = new Cookie(UserServiceApi.COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(BgUserKeyPrefix.TOKEN.expireSeconds());// 保持与redis中的session一致
        cookie.setPath("/");
        response.addCookie(cookie);
        // 返回登陆成功
        return Result.success(true);
    }

    /**
     * @description: 注册跳转
     * @author: Sakura
     * @date: 4/10/20
     * @return: java.lang.String
     **/
    @RequestMapping(value = "doRegister", method = RequestMethod.GET)
    public String doRegister() {
        logger.info("doRegister()");
        return "register";
    }

    /**
     * @description: 注册接口
     * @author: Sakura
     * @date: 4/10/20
     * @param registerVo:
     * @return: com.k2j.bargains.common.result.Result<java.lang.Boolean>
     **/
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> register(RegisterVo registerVo) {
        logger.info("RegisterVo = " + registerVo);

        if (registerVo == null) {
            throw new GlobalException(CodeMsg.FILL_REGISTER_INFO);
        }

        CodeMsg codeMsg = userService.register(registerVo);

        return Result.info(codeMsg);
    }
}
