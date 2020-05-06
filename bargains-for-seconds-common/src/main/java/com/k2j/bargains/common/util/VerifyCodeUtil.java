package com.k2j.bargains.common.util;

import com.k2j.bargains.common.api.bargains.vo.VerifyCodeVo;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @className: VerifyCodeUtil
 * @description: 生成验证码工具类
 * @author: Sakura
 * @date: 4/8/20
 **/
public class VerifyCodeUtil {

    /**
     * @description: 用于生成验证码中的运算符
     * @author: Sakura
     * @date: 4/8/20
     * @param null:
     * @return: null
     **/
    private static char[] ops = new char[]{'+', '-', '*'};

    /**
     * @description: 创建验证码
     * @author: Sakura
     * @date: 4/8/20
     * @return: com.k2j.bargains.common.api.bargains.vo.VerifyCodeVo
     **/
    public static VerifyCodeVo createVerifyCode() {

        // 验证码的宽和高
        int width = 80;
        int height = 32;

        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();

        // 计算表达式值，并把把验证码值存到redis中
        int expResult = calc(verifyCode);
        //输出图片和结果
        return new VerifyCodeVo(image, expResult);
    }

    /**
     * @description: 使用 ScriptEngine 计算验证码中的数学表达式的值
     * @author: Sakura
     * @date: 4/8/20
     * @param exp:
     * @return: int
     **/
    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer) engine.eval(exp);// 表达式计算
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @description: 生成验证码，只含有+/-/*
     * 随机生成三个数字，然后生成表达式
     * @author: Sakura
     * @date: 4/8/20
     * @param rdm:
     * @return: java.lang.String 验证码中的数学表达式
     **/
    private static String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = "" + num1 + op1 + num2 + op2 + num3;
        return exp;
    }
}
