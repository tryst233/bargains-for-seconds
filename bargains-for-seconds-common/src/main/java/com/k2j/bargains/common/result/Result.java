package com.k2j.bargains.common.result;

import java.io.Serializable;

/**
 * @className: Result
 * @description: 用户接口返回结果
 * @author: Sakura
 * @date: 4/7/20
 * @param <T> 数据实体类型
 **/
public class Result<T> implements Serializable {

    /**
     * @description: 状态码
     * @author: Sakura
     * @date: 4/7/20
     * @param null:
     * @return: null
     **/
    private int code;

    /**
     * @description: 状态短语
     * @author: Sakura
     * @date: 4/7/20
     * @param null:
     * @return: null
     **/
    private String msg;

    /**
     * @description: 数据实体
     * @author: Sakura
     * @date: 4/7/20
     * @param null:
     * @return: null
     **/
    private T data;

    /**
     * @description: 定义为private是为了在防止在controller中直接new
     * @author: Sakura
     * @date: 4/7/20
     * @param data:
     * @return: null
     **/
    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg == null)
            return;
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    /**
     * @description: 只有get没有set，是为了防止在controller使用set对结果修改，从而达到一个更好的封装效果
     * @author: Sakura
     * @date: 4/7/20
     * @return: int
     **/
    public int getCode() {
        return code;
    }

    /**
     * @description: 业务处理成功返回结果，直接返回业务数据
     * @author: Sakura
     * @date: 4/7/20
     * @param data:
     * @return: com.k2j.bargains.common.result.Result<T>
     **/
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * @description: 业务处理信息
     * @author: Sakura
     * @date: 4/7/20
     * @param serverInfo:
     * @return: com.k2j.bargains.common.result.Result<T>
     **/
    public static <T> Result<T> info(CodeMsg serverInfo) {
        return new Result<>(serverInfo);
    }

    /**
     * @description: 业务处理成功
     * @author: Sakura
     * @date: 4/7/20
     * @param serverSuccess:
     * @return: com.k2j.bargains.common.result.Result<T>
     **/
    public static <T> Result<T> success(CodeMsg serverSuccess) {
        return new Result<>(serverSuccess);
    }

    /**
     * @description: 业务处理失败
     * @author: Sakura
     * @date: 4/7/20
     * @param serverError:
     * @return: com.k2j.bargains.common.result.Result<T>
     **/
    public static <T> Result<T> error(CodeMsg serverError) {
        return new Result<>(serverError);
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
