package com.k2j.bargains.mq.service;

import com.k2j.bargains.common.api.mq.MqProviderApi;
import com.k2j.bargains.common.api.mq.vo.BgMessage;
import com.k2j.bargains.mq.config.MQConfig;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @className: MqProviderImpl
 * @description: 消息队列服务化（消息生产者）
 * @author: Sakura
 * @date: 4/9/20
 **/
@Service(interfaceClass = MqProviderApi.class)
public class MqProviderImpl implements MqProviderApi, RabbitTemplate.ConfirmCallback {

    private static Logger logger = LoggerFactory.getLogger(MqProviderImpl.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MqProviderImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // 设置 ack 回调
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void sendBgMessage(BgMessage message) {
        logger.info("MQ send message: " + message);
        // 秒杀消息关联的数据
        CorrelationData bgCorrData = new CorrelationData(UUID.randomUUID().toString());
        // 第一个参数为消息队列名(此处也为routingKey)，第二个参数为发送的消息
        rabbitTemplate.convertAndSend(MQConfig.BARGAINS_QUEUE, message, bgCorrData);
    }

    /**
     * @description: MQ ack 机制
     * @author: Sakura
     * @date: 4/9/20
     * @param correlationData:
     * @param ack:
     * @param cause:
     * @return: void
     **/
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("BgMessage UUID: " + correlationData.getId());
        if (ack) {
            logger.info("BgMessage 消息消费成功！");
        } else {
            System.out.println("BgMessage 消息消费失败！");
        }

        if (cause != null) {
            logger.info("CallBackConfirm Cause: " + cause);
        }
    }
}
