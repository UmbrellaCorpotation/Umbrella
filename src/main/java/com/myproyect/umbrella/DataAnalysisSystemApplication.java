package com.myproyect.umbrella;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class DataAnalysisSystemApplication  {

    @Autowired

    public static void main(String[] args) {
        SpringApplication.run(DataAnalysisSystemApplication.class, args);
    }


}
