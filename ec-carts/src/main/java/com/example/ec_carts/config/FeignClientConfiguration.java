package com.example.ec_carts.config;

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
            if (url.contains("/jwt")) {
                serviceIdThreadLocal.set("ec-oauth");
            } else if (url.contains("/products")) {
                serviceIdThreadLocal.set("ec-products");
            } else if (url.contains("/new")) {
                serviceIdThreadLocal.set("ec-wishlist");
            }
        };
    }
    
}
