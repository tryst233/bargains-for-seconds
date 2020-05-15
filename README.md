# 分布式秒杀系统

- [介绍](#介绍) 
- [运行](#运行) 
- [系统架构](#系统架构)
- [文档说明](#文档说明)
- [参考](#参考)   

## 介绍

​		本项目为使用SpringBoot+Zookeeper+Dubbo+rabbitMQ实现的分布式高并发秒杀系统，项目中使用了诸多优化手段，使系统能够满足秒杀业务场景下的高并发要求。

## 运行

- 开发环境：

  JDK 1.8、Mysql 8.0.12、SpringBoot 2.1.5、zookeeper 3.4.10、dubbo 2.7.1、redis 5.0.5、rabbitMQ 3.7.15

- 构建工具：

  apache-maven-3.6.1

运行之前请确认已经安装好上述开发环境和工具，并启动相关服务，如Redis、zookeeper、rabbitMQ等。

运行步骤：

1. 初始化数据库：

   使用./bargains-for-seconds-common/schema/bargains.sql初始化数据库。

2. 可以使用Git快速启动：

   ``git clone git@github.com:Lasolitude/bargains-for-seconds.git``

   ``mvn clean package``

3. 也可以在Linux环境或者导入IDE中依次启动以下服务：

   运行缓存服务：

   ``java -jar bargains-for-seconds-cache/target/bargains-for-seconds-cache-0.0.1-SNAPSHOT.jar``

   运行用户服务：

   ``java -jar bargains-for-seconds-user/target/bargains-for-seconds-user-0.0.1-SNAPSHOT.jar``

   运行订单服务：

   ``java -jar bargains-for-seconds-order/target/bargains-for-seconds-order-0.0.1-SNAPSHOT.jar``

   运行商品服务：

   ``java -jar bargains-for-seconds-goods/target/bargains-for-seconds-goods-0.0.1-SNAPSHOT.jar``

   运行消息队列服务：

   ``java -jar bargains-for-seconds-mq/target/bargains-for-seconds-mq-0.0.1-SNAPSHOT.jar``

   运行网关服务：

   ``java -jar bargains-for-seconds-gateway/target/bargains-for-seconds-gateway-0.0.1-SNAPSHOT.jar``

4. 访问地址：<http://localhost:8080/user/index>，注册登录即可。

## 系统架构

![秒杀系统架构图](https://cdn.jsdelivr.net/gh/Lasolitude/BlogStaticFile/BlogPictures/秒杀系统架构图.jpg)

- 注册中心使用zookeeper，实际上zookeeper的CP模型并不适合作为注册中心来使用，因为zookeeper是一个分布式协调系统，但是对于服务发现，可用性应该放在第一位，强调的是高可用，zookeeper更适合与kafka、hbase、Hadoop等一起使用；
- 缓存采用Redis；
- 消息队列使用的是rabbitMQ，为了保证消息不丢失，需要对消息进行持久化处理；
- 用户请求全部经由Gateway模块处理；
- Gateway模块使用RPC的方式调用其他模块提供的服务完成业务处理。

## 文档说明

1. 模块说明  
   - bargains-for-seconds-common：公共通用模块
   - bargains-for-seconds-user：用户模块
   - bargains-for-seconds-goods：商品模块
   - bargains-for-seconds-order：订单模块
   - bargains-for-seconds-gateway：网关模块
   - bargains-for-seconds-cache：缓存模块
   - bargains-for-seconds-mq：消息队列模块

2. [前后端交互](https://github.com/Lasolitude/bargains-for-seconds/blob/master/docs/%E5%89%8D%E5%90%8E%E7%AB%AF%E4%BA%A4%E4%BA%92.md "前后端交互")
3. [前后端交互接口逻辑实现](https://github.com/Lasolitude/bargains-for-seconds/blob/master/docs/%E5%89%8D%E5%90%8E%E7%AB%AF%E4%BA%A4%E4%BA%92%E6%8E%A5%E5%8F%A3%E9%80%BB%E8%BE%91%E5%AE%9E%E7%8E%B0.md "前后端交互接口逻辑实现")

## 参考

- https://github.com/Grootzz/dis-seckill
- https://github.com/qiurunze123/miaosha