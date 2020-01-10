package com.mlb.orderserviceconsumer.config;

import com.mlb.orderserviceconsumer.Interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mlb
 */

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(new SessionInterceptor()).excludePathPatterns("/property/login");
    }
}
