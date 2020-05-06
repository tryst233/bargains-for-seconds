package com.k2j.bargains.gateway.exception;

import com.k2j.bargains.common.result.CodeMsg;

/**
 * @className: GlobalException
 * @description: 全局异常处理器
 * @author: Sakura
 * @date: 4/9/20
 **/
public class GlobalException extends RuntimeException {

    private CodeMsg codeMsg;

    /**
     * @description: 使用构造器接收CodeMsg
     * @author: Sakura
     * @date: 4/9/20
     * @param codeMsg:
     * @return: null
     **/
    public GlobalException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
