package com.myproyect.umbrella;

import com.myproyect.umbrella.service.MuestraService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner run(ApplicationContext appContext) {
        return args -> {
            // Obtener el bean de MuestraService y llamar al metodo procesarYAgruparMuestras
            MuestraService muestraService = appContext.getBean(MuestraService.class);

            muestraService.procesarYAgruparMuestras();

            System.out.println("Procesamiento y agrupación de muestras completado.");
        };
    }
}
