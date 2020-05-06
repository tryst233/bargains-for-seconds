package com.k2j.bargains.common.api.cache.vo;

/**
 * @className: BaseKeyPrefix
 * @description: 模板方法的基本类
 * @author: Sakura
 * @date: 4/6/20
 **/
public abstract class BaseKeyPrefix implements KeyPrefix {

    int expireSeconds;//过期时间
    String prefix;//前缀

    /**
     * @description: 默认过期时间为0，即不过期，过期时间受到redis的缓存策略影响
     * @author: Sakura
     * @date: 4/6/20
     * @param prefix:
     * @return: null
     **/
    public BaseKeyPrefix(String prefix) {
        this(0, prefix);
    }

    public BaseKeyPrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    /**
     * @description: 前缀为模板类的实现类的类名
     * @author: Sakura
     * @date: 4/6/20

     * @return: java.lang.String
     **/
    @Override
    public String getPrefix() {
        String simpleName = getClass().getSimpleName();
        return simpleName + ":" + prefix;
    }
}
