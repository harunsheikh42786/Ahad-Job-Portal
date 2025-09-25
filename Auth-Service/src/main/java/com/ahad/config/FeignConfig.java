package com.ahad.config;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfig {

    @Bean
    public Request.Options options() {
        return new Request.Options(
                5000L, TimeUnit.MILLISECONDS, // connectTimeout: 5000ms = 5 seconds
                5000L, TimeUnit.MILLISECONDS, // readTimeout: 5000ms = 5 seconds
                true // followRedirects
        );
    }
}