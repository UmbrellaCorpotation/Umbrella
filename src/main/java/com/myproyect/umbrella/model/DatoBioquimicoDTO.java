package com.myproyect.umbrella.model;


import com.myproyect.umbrella.domain.Muestra;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatoBioquimicoDTO extends Muestra{

    private String name;
    private String category;
    private String dosageForm;
    private String strength;
    private String manufacturer;
    private String indication;
    private String classification;
}
