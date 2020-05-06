package com.k2j.bargains.common.api.user;

import com.k2j.bargains.common.api.user.vo.LoginVo;
import com.k2j.bargains.common.api.user.vo.RegisterVo;
import com.k2j.bargains.common.api.user.vo.UserInfoVo;
import com.k2j.bargains.common.api.user.vo.UserVo;
import com.k2j.bargains.common.result.CodeMsg;

import javax.validation.Valid;

/**
 * @className: UserServiceApi
 * @description: 用于用户交互api
 * @author: Sakura
 * @date: 4/6/20
 **/
public interface UserServiceApi {

    String COOKIE_NAME_TOKEN = "token";

    /**
     * @description: 登录
     * 返回用户id；用户登录成功后，将用户id从后台传到前台，通过JWT的形式传到客户端，
     * 用户再次访问的时候，就携带JWT到服务端，服务端用一个缓存（如redis）将JWT缓存起来，
     * 并设置有效期，这样，用户不用每次访问都到数据库中查询用户id
     * @author: Sakura 
     * @date: 4/6/20
     * @param username: 
     * @param password: 
     * @return: int
     **/
    int login(String username, String password);

    /**
     * @description: 注册
     * @author: Sakura
     * @date: 4/6/20
     * @param userModel:
     * @return: com.k2j.bargains.common.result.CodeMsg
     **/
    CodeMsg register(RegisterVo userModel);

    /**
     * @description: 检查用户名是否存在
     * @author: Sakura
     * @date: 4/6/20
     * @param username:
     * @return: boolean
     **/
    boolean checkUsername(String username);

    /**
     * @description: 获取用户信息
     * @author: Sakura
     * @date: 4/6/20
     * @param uuid:
     * @return: com.k2j.bargains.common.api.user.vo.UserInfoVo
     **/
    UserInfoVo getUserInfo(int uuid);

    /**
     * @description: 更新用户信息
     * @author: Sakura
     * @date: 4/6/20
     * @param userInfoVo:
     * @return: com.k2j.bargains.common.api.user.vo.UserInfoVo
     **/
    UserInfoVo updateUserInfo(UserInfoVo userInfoVo);

    /**
     * @description: 登录
     * @author: Sakura
     * @date: 4/6/20
     * @param loginVo:
     * @return: java.lang.String
     **/
    String login(@Valid LoginVo loginVo);

    /**
     * @description: 根据phone获取用户
     * @author: Sakura
     * @date: 4/6/20
     * @param phone:
     * @return: com.k2j.bargains.common.api.user.vo.UserVo
     **/
    UserVo getUserByPhone(long phone);
    
}
