package com.k2j.bargains.user.persistence;

import com.k2j.bargains.user.domain.BargainsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @className: BargainsUserMapper
 * @description: bargains_user表交互接口
 * @author: Sakura
 * @date: 4/8/20
 **/
@Mapper
public interface BargainsUserMapper {

    /**
     * @description: 通过 phone 查询用户信息
     * @author: Sakura
     * @date: 4/8/20
     * @param phone:
     * @return: com.k2j.bargains.common.domain.BargainsUser
     **/
    BargainsUser getUserByPhone(@Param("phone") Long phone);

    /**
     * @description: 更新用户信息
     * @author: Sakura
     * @date: 4/8/20
     * @param updatedUser:
     * @return: void
     **/
    @Update("UPDATE bargains_user SET password=#{password} WHERE id=#{id}")
    void updatePassword(BargainsUser updatedUser);

    /**
     * @description: 插入一条用户信息到数据库中
     * @author: Sakura
     * @date: 4/8/20
     * @param bargainsUser:
     * @return: long
     **/
    long insertUser(BargainsUser bargainsUser);

    /**
     * @description: 查询电话号码
     * @author: Sakura
     * @date: 4/8/20
     * @param phone:
     * @return: long
     **/
    long findPhone(long phone);
}
