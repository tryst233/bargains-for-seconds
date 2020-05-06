package com.k2j.bargains.gateway.config.interceptor;

import com.k2j.bargains.common.api.user.vo.UserVo;

/**
 * @className: UserContext
 * @description: 用于保存用户
 * 使用ThreadLocal保存用户，因为ThreadLocal是线程安全的，使用ThreadLocal可以保存当前线程持有的对象
 * 每个用户的请求对应一个线程，所以使用ThreadLocal以线程为键保存用户是合适的
 * @author: Sakura
 * @date: 4/9/20
 **/
public class UserContext {

    // 保存用户的容器
    private static ThreadLocal<UserVo> userHolder = new ThreadLocal<>();

    public static UserVo getUser() {
        return userHolder.get();
    }

    public static void setUser(UserVo user) {
        userHolder.set(user);
    }
}
