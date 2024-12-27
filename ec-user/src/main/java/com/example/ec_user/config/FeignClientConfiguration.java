package com.example.ec_user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class FeignClientConfiguration {

    public static final ThreadLocal<String> serviceIdThreadLocal = new ThreadLocal<>();
    public static String publicUrl;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String url = requestTemplate.url();
            publicUrl = url;
            if (url.contains("/cart")) {
                serviceIdThreadLocal.set("ec-cart");
            } else if (url.contains("/wishlist")) {
                serviceIdThreadLocal.set("ec-wishlist");
            }
        };
    }
    
}
