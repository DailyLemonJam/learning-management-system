package com.leverx.learningmanagementsystem.core.async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfiguration {

    public static final String ASYNC_EMAIL_SENDER_EXECUTOR = "asyncEmailSenderExecutor";

    @Bean(name = ASYNC_EMAIL_SENDER_EXECUTOR)
    public Executor asyncEmailSenderThreadPoolExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setQueueCapacity(5);
        executor.setMaxPoolSize(50);
        executor.setKeepAliveSeconds(120);
        executor.setAllowCoreThreadTimeOut(false);
        executor.initialize();
        return executor;
    }
}
