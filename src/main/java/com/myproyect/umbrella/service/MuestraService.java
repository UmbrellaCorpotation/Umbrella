package com.myproyect.umbrella.service;


import com.myproyect.umbrella.model.Muestra;
import com.myproyect.umbrella.repository.MuestraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MuestraService {

    @Autowired
    private MuestraRepository muestraRepository;

    public List<Muestra> getAllMuestras() {
        return muestraRepository.findAll();
    }

    public Optional<Muestra> getMuestraById(Integer id) {
        return muestraRepository.findById(id);
    }

    public Muestra saveMuestra(Muestra muestra) {
        return muestraRepository.save(muestra);
    }

    public void deleteMuestra(Integer id) {
        muestraRepository.deleteById(id);
    }
}
