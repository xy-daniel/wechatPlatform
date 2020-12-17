package com.crudsavior.wechatPlatform.config;

import com.crudsavior.wechatPlatform.interceptor.WxInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig
 *
 * @author arctic
 * @date 2020/12/10
 **/
@Configuration
@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public WxInterceptor wxInterceptor () {
        return new WxInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(wxInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/menu/**", "/admin/**", "/favicon.ico");
    }
}
