package com.k2j.bargains.common.util;

import com.alibaba.fastjson.JSON;
/**
 * @className: JsonUtil
 * @description: json 工具类
 * @author: Sakura
 * @date: 4/7/20
 **/
public class JsonUtil {

    /**
     * @description: 根据传入的class参数，将json字符串转换为对应类型的对象
     * @author: Sakura
     * @date: 4/7/20
     * @param strValue: json字符串
     * @param clazz: 类型
     * @return: T json字符串对应的对象
     **/
    public static <T> T stringToBean(String strValue, Class<T> clazz) {

        if ((strValue == null) || (strValue.length() <= 0) || (clazz == null))
            return null;

        // int or Integer
        if ((clazz == int.class) || (clazz == Integer.class))
            return (T) Integer.valueOf(strValue);
            // long or Long
        else if ((clazz == long.class) || (clazz == Long.class))
            return (T) Long.valueOf(strValue);
            // String
        else if (clazz == String.class)
            return (T) strValue;
            // 对象类型
        else
            return JSON.toJavaObject(JSON.parseObject(strValue), clazz);
    }

    /**
     * @description: 将对象转换为对应的json字符串
     * @author: Sakura
     * @date: 4/7/20
     * @param value: 对象
     * @return: java.lang.String 对象对应的json字符串
     **/
    public static <T> String beanToString(T value) {
        if (value == null)
            return null;

        Class<?> clazz = value.getClass();
        /*首先对基本类型处理*/
        if (clazz == int.class || clazz == Integer.class)
            return "" + value;
        else if (clazz == long.class || clazz == Long.class)
            return "" + value;
        else if (clazz == String.class)
            return (String) value;
            /*然后对Object类型的对象处理*/
        else
            return JSON.toJSONString(value);
    }
}
