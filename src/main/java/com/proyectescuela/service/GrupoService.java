package com.proyectescuela.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectescuela.model.Carrera;
import com.proyectescuela.model.Grado;
import com.proyectescuela.model.Grupo;
import com.proyectescuela.model.Turno;
import com.proyectescuela.repo.CarreraRepository;
import com.proyectescuela.repo.GradoRepository;
import com.proyectescuela.repo.GrupoRepository;
import com.proyectescuela.repo.TurnoRepository;
import com.proyectescuela.web.dto.GrupoRequest;
import com.proyectescuela.web.dto.GrupoResponse;

@Service
public class GrupoService {
    private final GrupoRepository grupoRepository;
    private final CarreraRepository carreraRepository;
    private final TurnoRepository turnoRepository;
    private final GradoRepository gradoRepository;

    public GrupoService(
            GrupoRepository grupoRepository,
            CarreraRepository carreraRepository,
            TurnoRepository turnoRepository,
            GradoRepository gradoRepository) {
        this.grupoRepository = grupoRepository;
        this.carreraRepository = carreraRepository;
        this.turnoRepository = turnoRepository;
        this.gradoRepository = gradoRepository;
    }

    @Transactional(readOnly = true)
    public List<GrupoResponse> list(boolean includeInactivos) {
        List<Grupo> data = includeInactivos ? grupoRepository.findAll() : grupoRepository.findAllByActivoTrue();
        return data.stream().map(GrupoResponse::from).toList();
    }

    @Transactional
    public GrupoResponse create(GrupoRequest request) {
        Carrera carrera = carreraRepository.findById(request.getCarreraId())
                .orElseThrow(() -> new NotFoundException("Carrera no encontrada"));
        Turno turno = turnoRepository.findById(request.getTurnoId())
                .orElseThrow(() -> new NotFoundException("Turno no encontrado"));
        Grado grado = gradoRepository.findById(request.getGradoId())
                .orElseThrow(() -> new NotFoundException("Grado no encontrado"));
        validarActivos(carrera, turno, grado);

        int next = nextConsecutivo(carrera.getId(), turno.getId(), grado.getId());
        String codigo = buildCodigo(carrera.getSigla(), grado.getNumero(), next, turno.getSigla());

        Grupo grupo = new Grupo();
        grupo.setCarrera(carrera);
        grupo.setTurno(turno);
        grupo.setGrado(grado);
        grupo.setConsecutivo(next);
        grupo.setCodigo(codigo);
        grupo.setActivo(true);

        try {
            Grupo saved = grupoRepository.save(grupo);
            Grupo reloaded = grupoRepository.findById(saved.getId())
                    .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
            return GrupoResponse.from(reloaded);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            int retry = next + 1;
            while (grupoRepository.existsByCarreraIdAndTurnoIdAndGradoIdAndConsecutivo(
                    carrera.getId(), turno.getId(), grado.getId(), retry)) {
                retry++;
            }
            grupo.setConsecutivo(retry);
            grupo.setCodigo(buildCodigo(carrera.getSigla(), grado.getNumero(), retry, turno.getSigla()));
            Grupo saved = grupoRepository.save(grupo);
            Grupo reloaded = grupoRepository.findById(saved.getId())
                    .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
            return GrupoResponse.from(reloaded);
        }
    }

    @Transactional
    public GrupoResponse update(Long id, GrupoRequest request) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        Carrera carrera = carreraRepository.findById(request.getCarreraId())
                .orElseThrow(() -> new NotFoundException("Carrera no encontrada"));
        Turno turno = turnoRepository.findById(request.getTurnoId())
                .orElseThrow(() -> new NotFoundException("Turno no encontrado"));
        Grado grado = gradoRepository.findById(request.getGradoId())
                .orElseThrow(() -> new NotFoundException("Grado no encontrado"));
        validarActivos(carrera, turno, grado);

        int next = nextConsecutivo(carrera.getId(), turno.getId(), grado.getId());
        String codigo = buildCodigo(carrera.getSigla(), grado.getNumero(), next, turno.getSigla());

        grupo.setCarrera(carrera);
        grupo.setTurno(turno);
        grupo.setGrado(grado);
        grupo.setConsecutivo(next);
        grupo.setCodigo(codigo);

        try {
            Grupo saved = grupoRepository.save(grupo);
            Grupo reloaded = grupoRepository.findById(saved.getId())
                    .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
            return GrupoResponse.from(reloaded);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            int retry = next + 1;
            while (grupoRepository.existsByCarreraIdAndTurnoIdAndGradoIdAndConsecutivo(
                    carrera.getId(), turno.getId(), grado.getId(), retry)) {
                retry++;
            }
            grupo.setConsecutivo(retry);
            grupo.setCodigo(buildCodigo(carrera.getSigla(), grado.getNumero(), retry, turno.getSigla()));
            Grupo saved = grupoRepository.save(grupo);
            Grupo reloaded = grupoRepository.findById(saved.getId())
                    .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
            return GrupoResponse.from(reloaded);
        }
    }

    private void validarActivos(Carrera carrera, Turno turno, Grado grado) {
        if (!carrera.isActivo()) {
            throw new IllegalArgumentException("Carrera inactiva");
        }
        if (!turno.isActivo()) {
            throw new IllegalArgumentException("Turno inactivo");
        }
        if (!grado.isActivo()) {
            throw new IllegalArgumentException("Grado inactivo");
        }
    }

    private int nextConsecutivo(Long carreraId, Long turnoId, Long gradoId) {
        Integer max = grupoRepository.findMaxConsecutivo(carreraId, turnoId, gradoId);
        int next = (max == null ? 0 : max) + 1;
        while (grupoRepository.existsByCarreraIdAndTurnoIdAndGradoIdAndConsecutivo(carreraId, turnoId, gradoId, next)) {
            next++;
        }
        return next;
    }

    private String buildCodigo(String siglaCarrera, Integer gradoNumero, int consecutivo, String siglaTurno) {
        String grado = String.format("%02d", gradoNumero == null ? 0 : gradoNumero);
        String cons = String.format("%02d", consecutivo);
        return siglaCarrera + grado + cons + "-" + siglaTurno;
    }

    @Transactional
    public void setActivo(Long id, boolean activo) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        grupo.setActivo(activo);
        grupoRepository.save(grupo);
    }
}
