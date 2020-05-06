package com.k2j.bargains.common.util;

import java.util.UUID;

/**
 * @className: UUIDUtil
 * @description: UUID工具类用于生成session
 * @author: Sakura
 * @date: 4/8/20
 **/
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
