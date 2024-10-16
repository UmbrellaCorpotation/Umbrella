package com.myproyect.umbrella.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AppConfig {

    @Bean
    public Executor taskExecutor() {
        return Executors.newFixedThreadPool(5); // Configura un pool de hilos con 5 hilos
    }
}
