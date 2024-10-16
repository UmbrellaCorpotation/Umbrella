package com.myproyect.umbrella;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.myproyect.umbrella.domain.DatoGenetico;
import com.myproyect.umbrella.service.DatoGeneticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class DataAnalysisSystemApplication implements CommandLineRunner {

    @Autowired
    private DatoGeneticoService datoGeneticoService;

    public static void main(String[] args) {
        SpringApplication.run(DataAnalysisSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear un DatoGenetico y guardarlo en la base de datos
        DatoGenetico datoGenetico = new DatoGenetico();
        datoGenetico.setSecuenciaADN("AGCTTAGGCTA");
        datoGenetico.setObservaciones("Secuencia genética de prueba");

        // Guardar el DatoGenetico en la base de datos
        DatoGenetico datoGuardado = datoGeneticoService.createDatoGenetico(datoGenetico);
        System.out.println("DatoGenetico creado con ID: " + datoGuardado.getId());

        // Recuperar el DatoGenetico por su ID
        DatoGenetico datoRecuperado = datoGeneticoService.getDatoGeneticoById(datoGuardado.getId());
        System.out.println("DatoGenetico recuperado: " + datoRecuperado.getSecuenciaADN() + " - Observaciones: " + datoRecuperado.getObservaciones());
    }
}
