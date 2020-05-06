package com.k2j.bargains.common.api.bargains.vo;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @className: VerifyCodeVo
 * @description: 验证码图片及计算结果
 * @author: Sakura
 * @date: 4/7/20
 **/
public class VerifyCodeVo implements Serializable {

    /**
     * @description: 验证码图片
     * @author: Sakura
     * @date: 4/7/20
     * @param null:
     * @return: null
     **/
    private BufferedImage image;

    /**
     * @description: 验证码计算结果
     * @author: Sakura
     * @date: 4/7/20
     * @param null:
     * @return: null
     **/
    private int expResult;

    public VerifyCodeVo() {
    }

    public VerifyCodeVo(BufferedImage image, int expResult) {
        this.image = image;
        this.expResult = expResult;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getExpResult() {
        return expResult;
    }

    public void setExpResult(int expResult) {
        this.expResult = expResult;
    }
}
