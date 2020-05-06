package com.k2j.bargains.gateway;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @className: BargainsServletInitializer
 * @description: web app 程序启动器
 * @author: Sakura
 * @date: 4/9/20
 **/
public class BargainsServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GatewayApplication.class);
    }
}
