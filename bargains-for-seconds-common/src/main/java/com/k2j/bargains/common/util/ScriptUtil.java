package com.k2j.bargains.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @className: ScriptUtil
 * @description: lua 脚本工具类
 * @author: Sakura
 * @date: 5/2/20
 **/
public class ScriptUtil {

    /**
     * @description: 读取 lua 脚本
     * @author: Sakura
     * @date: 5/2/20
     * @param path:
     * @return: java.lang.String
     **/
    public static String getScript(String path) {
        StringBuilder sb = new StringBuilder();

        InputStream stream = ScriptUtil.class.getClassLoader().getResourceAsStream(path);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream)))
        {
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str).append(System.lineSeparator());
            }

        } catch (IOException e) {
            System.err.println(e.getStackTrace());
        }
        return sb.toString();
    }
}
