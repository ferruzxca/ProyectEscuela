package com.proyectescuela.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectescuela.model.Carrera;
import com.proyectescuela.repo.CarreraRepository;
import com.proyectescuela.web.dto.CarreraRequest;
import com.proyectescuela.web.dto.CarreraResponse;

@Service
public class CarreraService {
    private final CarreraRepository carreraRepository;

    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Transactional(readOnly = true)
    public List<CarreraResponse> list() {
        return carreraRepository.findAll().stream().map(CarreraResponse::from).toList();
    }

    @Transactional
    public CarreraResponse create(CarreraRequest request) {
        Carrera carrera = new Carrera();
        carrera.setNombre(request.getNombre());
        carrera.setSigla(request.getSigla());
        carrera.setActivo(true);
        Carrera saved = carreraRepository.save(carrera);
        return CarreraResponse.from(saved);
    }
}
