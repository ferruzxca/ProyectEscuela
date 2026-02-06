package com.proyectescuela.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectescuela.model.Alumno;
import com.proyectescuela.model.Grupo;
import com.proyectescuela.repo.AlumnoRepository;
import com.proyectescuela.repo.GrupoRepository;
import com.proyectescuela.web.dto.AlumnoRequest;
import com.proyectescuela.web.dto.AlumnoResponse;

@Service
public class AlumnoService {
    private final AlumnoRepository alumnoRepository;
    private final GrupoRepository grupoRepository;

    public AlumnoService(AlumnoRepository alumnoRepository, GrupoRepository grupoRepository) {
        this.alumnoRepository = alumnoRepository;
        this.grupoRepository = grupoRepository;
    }

    @Transactional(readOnly = true)
    public List<AlumnoResponse> list(boolean includeInactivos) {
        List<Alumno> data = includeInactivos ? alumnoRepository.findAll() : alumnoRepository.findAllByActivoTrue();
        return data.stream().map(AlumnoResponse::from).toList();
    }

    @Transactional
    public AlumnoResponse create(AlumnoRequest request) {
        Grupo grupo = grupoRepository.findById(request.getGrupoId())
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));

        Alumno alumno = new Alumno();
        alumno.setMatricula(request.getMatricula());
        alumno.setNombre(request.getNombre());
        alumno.setApellidos(request.getApellidos());
        alumno.setGrupo(grupo);
        alumno.setActivo(true);

        Alumno saved = alumnoRepository.save(alumno);
        Alumno reloaded = alumnoRepository.findById(saved.getId())
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
        return AlumnoResponse.from(reloaded);
    }

    @Transactional
    public void setActivo(Long id, boolean activo) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
        alumno.setActivo(activo);
        alumnoRepository.save(alumno);
    }
}
