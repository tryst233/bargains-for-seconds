package com.k2j.bargains.gateway.config;

import com.k2j.bargains.gateway.config.interceptor.AccessInterceptor;
import com.k2j.bargains.gateway.config.resolver.UserArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @className: WebConfig
 * @description: 自定义web配置
 * @author: Sakura
 * @date: 4/9/20
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Autowired
    UserArgumentResolver userArgumentResolver;

    @Autowired
    AccessInterceptor accessInterceptor;

    /**
     * @description: 添加自定义的参数解析器到MVC配置中
     * @author: Sakura
     * @date: 4/9/20
     * @param argumentResolvers:
     * @return: void
     **/
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        logger.info("添加自定义的参数解析器");
        // 添加自定义的参数解析器
        argumentResolvers.add(userArgumentResolver);
    }

    /**
     * @description: 添加自定义方法拦截器到MVC配置中
     * @author: Sakura
     * @date: 4/9/20
     * @param registry:
     * @return: void
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("添加请求拦截器");
        registry.addInterceptor(accessInterceptor);
    }
}
