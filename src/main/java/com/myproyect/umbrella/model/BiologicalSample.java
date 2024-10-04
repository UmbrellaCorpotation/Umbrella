package com.myproyect.umbrella.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BiologicalSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // genético, bioquímico, físico

    @Lob
    private String data; // datos crudos para procesar

    private String processedData; // datos procesados
}
