package com.k2j.bargains.mq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @className: MQConfig
 * @description: 通过配置文件获取消息队列
 * @author: Sakura
 * @date: 4/9/20
 **/
@Configuration
public class MQConfig {

    /**
     * @description: 消息队列名
     * @author: Sakura
     * @date: 4/9/20
     * @param null:
     * @return: null
     **/
    public static final String BARGAINS_QUEUE = "bargains.queue";

    /**
     * @description: 秒杀 routing key, 生产者沿着 routingKey 将消息投递到 exchange 中
     * @author: Sakura
     * @date: 4/9/20
     * @param null:
     * @return: null
     **/
    public static final String BG_ROUTING_KEY = "routing.bg";

    /**
     * @description: Direct模式 交换机exchange
     * 生成用于秒杀的queue
     * @author: Sakura
     * @date: 4/9/20
     * @return: org.springframework.amqp.core.Queue
     **/
    @Bean
    public Queue bargainsQueue() {
        return new Queue(BARGAINS_QUEUE, true);
    }

    /**
     * @description: 实例化 RabbitTemplate
     * @author: Sakura
     * @date: 4/9/20
     * @param connectionFactory:
     * @return: org.springframework.amqp.rabbit.core.RabbitTemplate
     **/
    @Bean
    @Scope("prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }
}
