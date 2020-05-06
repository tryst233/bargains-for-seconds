package com.k2j.bargains.common.api.mq;

import com.k2j.bargains.common.api.mq.vo.BgMessage;

/**
 * @className: MqProviderApi
 * @description: 消息队列服务
 * @author: Sakura
 * @date: 4/7/20
 **/
public interface MqProviderApi {

    /**
     * @description: 将用户秒杀信息投递到MQ中（使用direct模式的exchange）
     * @author: Sakura
     * @date: 4/7/20
     * @param message:
     * @return: void
     **/
    void sendBgMessage(BgMessage message);
}
