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
    public List<TurnoResponse> list() {
        return turnoRepository.findAll().stream().map(TurnoResponse::from).toList();
    }

    @Transactional
    public TurnoResponse create(TurnoRequest request) {
        Turno turno = new Turno();
        turno.setNombre(request.getNombre());
        turno.setSigla(request.getSigla());
        turno.setActivo(true);
        Turno saved = turnoRepository.save(turno);
        return TurnoResponse.from(saved);
    }
}
