package com.k2j.bargains.common.api.user.vo;

import com.k2j.bargains.common.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @className: LoginVo
 * @description: 用于接收客户端请求中的表单数据，使用JSR303完成参数校验
 * @author: Sakura
 * @date: 4/6/20
 **/
public class LoginVo implements Serializable {

    /**
     * @description: 通过注解的方式校验手机号（JSR303）
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    @NotNull
    @IsMobile// 自定义的注解完成手机号的校验
    private String mobile;

    /**
     * @description: 通过注解的方式校验密码（JSR303）
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    @NotNull
    @Length(min = 32)// 长度最小为32
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
