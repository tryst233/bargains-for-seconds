package com.k2j.bargains.common.result;

import java.io.Serializable;

/**
 * @className: CodeMsg
 * @description: 响应结果状态码
 * @author: Sakura
 * @date: 4/6/20
 **/
public class CodeMsg implements Serializable {

    private int code;
    private String msg;

    /**
     * @description: 构造器定义为private是为了防止controller直接new
     * @author: Sakura
     * @date: 4/6/20
     * @param code:
     * @param msg:
     * @return: null
     **/
    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * @description: 通用异常模块 5001XX
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg VERIFY_FAIL = new CodeMsg(500103, "校验失败，请重新输入表达式结果或刷新校验码重新输入");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500104, "访问太频繁！");

    /**
     * @description: 用户模块 5002XX
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效，请返回登录！");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");
    public static CodeMsg USER_EXIST = new CodeMsg(500216, "用户已经存在，无需重复注册");
    public static CodeMsg REGISTER_SUCCESS = new CodeMsg(500217, "注册成功");
    public static CodeMsg REGISTER_FAIL = new CodeMsg(500218, "注册异常");
    public static CodeMsg FILL_REGISTER_INFO = new CodeMsg(500219, "请填写注册信息");
    public static CodeMsg WAIT_REGISTER_DONE = new CodeMsg(500220, "等待注册完成");

    /**
     * @description: 订单模块 5004XX
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单不存在");

    /**
     * @description: 秒杀模块 5005XX
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static CodeMsg BARGAINS_OVER = new CodeMsg(500500, "商品已经秒杀完毕");
    public static CodeMsg REPEAT_BARGAINS = new CodeMsg(500501, "不能重复秒杀");
    public static CodeMsg BARGAINS_FAIL = new CodeMsg(500502, "秒杀失败");
    public static CodeMsg BARGAINS_PARAM_ILLEGAL = new CodeMsg(500503, "秒杀请求参数异常：%s");

    /**
     * @description: 动态地填充msg字段
     * @author: Sakura
     * @date: 4/6/20
     * @param args:
     * @return: com.k2j.bargains.common.result.CodeMsg
     **/
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);// 将arg格式化到msg中，组合成一个message
        return new CodeMsg(code, message);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
