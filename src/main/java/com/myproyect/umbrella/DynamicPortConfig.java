package com.myproyect.umbrella;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.ServerSocket;

@Configuration
public class DynamicPortConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            int port = 8080; // Intenta con el puerto 8080
            if (!isPortAvailable(port)) {
                port = 8081; // Si no está disponible, cambia al 8081
            }
            factory.setPort(port);
        };
    }

    private boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
