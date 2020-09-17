package com.project.covid19.config;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@Log
public class AsyncThreadConfig {
    @Bean
    public Executor asyncThreadTestExecutor(){
        log.info("async Thread Pool init");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(25);
        threadPoolTaskExecutor.setMaxPoolSize(25);
        threadPoolTaskExecutor.setThreadNamePrefix("craw-Thread");
        return threadPoolTaskExecutor;
    }
}
