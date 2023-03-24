package com.mahov.config;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {

    @Bean
    public Random random(){
        return new Random();
    }

    @Bean
    public AtomicInteger atomicInteger(){
        return new AtomicInteger();
    }
}
