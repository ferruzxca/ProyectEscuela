package com.proyectescuela.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectescuela.model.Turno;
import com.proyectescuela.repo.TurnoRepository;
import com.proyectescuela.web.dto.TurnoRequest;
import com.proyectescuela.web.dto.TurnoResponse;

@Service
public class TurnoService {
    private final TurnoRepository turnoRepository;

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    @Transactional(readOnly = true)
    public List<TurnoResponse> list(boolean includeInactivos) {
        List<Turno> data = includeInactivos ? turnoRepository.findAll() : turnoRepository.findAllByActivoTrue();
        return data.stream().map(TurnoResponse::from).toList();
    }

    @Transactional
    public TurnoResponse create(TurnoRequest request) {
        if (turnoRepository.existsBySigla(request.getSigla())) {
            throw new IllegalArgumentException("Sigla de turno duplicada");
        }
        Turno turno = new Turno();
        turno.setNombre(request.getNombre());
        turno.setSigla(request.getSigla());
        turno.setActivo(true);
        Turno saved = turnoRepository.save(turno);
        return TurnoResponse.from(saved);
    }

    @Transactional
    public TurnoResponse update(Long id, TurnoRequest request) {
        if (turnoRepository.existsBySiglaAndIdNot(request.getSigla(), id)) {
            throw new IllegalArgumentException("Sigla de turno duplicada");
        }
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Turno no encontrado"));
        turno.setNombre(request.getNombre());
        turno.setSigla(request.getSigla());
        Turno saved = turnoRepository.save(turno);
        return TurnoResponse.from(saved);
    }

    @Transactional
    public void setActivo(Long id, boolean activo) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Turno no encontrado"));
        turno.setActivo(activo);
        turnoRepository.save(turno);
    }
}
