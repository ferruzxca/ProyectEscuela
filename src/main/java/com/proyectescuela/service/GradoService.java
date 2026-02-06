package com.proyectescuela.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectescuela.model.Grado;
import com.proyectescuela.repo.GradoRepository;
import com.proyectescuela.web.dto.GradoRequest;
import com.proyectescuela.web.dto.GradoResponse;

@Service
public class GradoService {
    private final GradoRepository gradoRepository;

    public GradoService(GradoRepository gradoRepository) {
        this.gradoRepository = gradoRepository;
    }

    @Transactional(readOnly = true)
    public List<GradoResponse> list(boolean includeInactivos) {
        List<Grado> data = includeInactivos ? gradoRepository.findAll() : gradoRepository.findAllByActivoTrue();
        return data.stream().map(GradoResponse::from).toList();
    }

    @Transactional
    public GradoResponse create(GradoRequest request) {
        Grado grado = new Grado();
        grado.setNombre(request.getNombre());
        grado.setNumero(request.getNumero());
        grado.setActivo(true);
        Grado saved = gradoRepository.save(grado);
        return GradoResponse.from(saved);
    }

    @Transactional
    public GradoResponse update(Long id, GradoRequest request) {
        Grado grado = gradoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grado no encontrado"));
        grado.setNombre(request.getNombre());
        grado.setNumero(request.getNumero());
        Grado saved = gradoRepository.save(grado);
        return GradoResponse.from(saved);
    }

    @Transactional
    public void setActivo(Long id, boolean activo) {
        Grado grado = gradoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grado no encontrado"));
        grado.setActivo(activo);
        gradoRepository.save(grado);
    }
}
