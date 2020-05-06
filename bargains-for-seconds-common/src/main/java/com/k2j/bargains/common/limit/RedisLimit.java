package com.k2j.bargains.common.limit;

import com.k2j.bargains.common.limit.constant.RedisToolsConstant;
import com.k2j.bargains.common.util.ScriptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.Collections;

/**
 * @className: RedisLimit
 * @description: 限流
 * @author: Sakura
 * @date: 5/2/20
 **/
public class RedisLimit {

    private static Logger logger = LoggerFactory.getLogger(RedisLimit.class);

    private JedisConnectionFactory jedisConnectionFactory;
    private int type ;
    private int limit = 200;

    private static final int FAIL_CODE = 0;

    /**
     * lua 脚本
     */
    private String script;

    private RedisLimit(Builder builder) {
        this.limit = builder.limit;
        this.jedisConnectionFactory = builder.jedisConnectionFactory;
        this.type = builder.type;
        buildScript();
    }

    /**
     * @description: 流限速
     * @author: Sakura
     * @date: 5/2/20
     * @return: boolean
     **/
    public boolean limit() {
        //get connection
        Object connection = getConnection();

        Object result = limitRequest(connection);

        if (FAIL_CODE != (Long) result) {
            return true;
        } else {
            return false;
        }
    }

    private Object limitRequest(Object connection) {
        Object result = null;
        String key = String.valueOf(System.currentTimeMillis() / 1000);
        if (connection instanceof Jedis) {
            result = ((Jedis)connection).eval(script, Collections.singletonList(key), Collections.singletonList(String.valueOf(limit)));
            ((Jedis) connection).close();
        } else {
            result = ((JedisCluster) connection).eval(script, Collections.singletonList(key), Collections.singletonList(String.valueOf(limit)));
            try {
                ((JedisCluster) connection).close();
            } catch (IOException e) {
                logger.error("IOException", e);
            }
        }
        return result;
    }

    /**
     * @description: 获取 Redis 连接
     * @author: Sakura
     * @date: 5/2/20
     * @return: java.lang.Object
     **/
    private Object getConnection() {
        Object connection;
        if (type == RedisToolsConstant.SINGLE) {
            RedisConnection redisConnection = jedisConnectionFactory.getConnection();
            connection = redisConnection.getNativeConnection();
        } else {
            RedisClusterConnection clusterConnection = jedisConnectionFactory.getClusterConnection();
            connection = clusterConnection.getNativeConnection();
        }
        return connection;
    }

    /**
     * @description: 读取 lua 脚本
     * @author: Sakura
     * @date: 5/2/20
     * @return: void
     **/
    private void buildScript() {
        script = ScriptUtil.getScript("limit.lua");
    }

    /**
     * @description: 静态内部类，Builder 模式
     * @author: Sakura
     * @date: 5/2/20
     * @param
     * @return: null
     **/
    public static class Builder {
        private JedisConnectionFactory jedisConnectionFactory = null;

        private int limit = 200;
        private int type;


        public Builder(JedisConnectionFactory jedisConnectionFactory, int type) {
            this.jedisConnectionFactory = jedisConnectionFactory;
            this.type = type;
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public RedisLimit build() {
            return new RedisLimit(this);
        }
    }
}
