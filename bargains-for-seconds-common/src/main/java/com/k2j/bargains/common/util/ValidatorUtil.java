package com.k2j.bargains.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @className: ValidatorUtil
 * @description: 手机号码格式校验工具
 * @author: Sakura
 * @date: 4/6/20
 **/
public class ValidatorUtil {

    /**
     * @description: 手机号正则
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    private static final Pattern mobilePattern = Pattern.compile("1\\d{10}");

    /**
     * @description: 正则表达式：验证手机号
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

    /**
     * @description: 校验手机号
     * @author: Sakura
     * @date: 4/6/20
     * @param mobile:
     * @return: boolean 校验通过返回true，否则返回false
     **/
    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile))
            return false;
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

}
