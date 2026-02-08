package com.proyectescuela.web;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.proyectescuela.service.AlumnoService;
import com.proyectescuela.web.dto.AlumnoRequest;
import com.proyectescuela.web.dto.AlumnoResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alumnos")
@Validated
public class AlumnoController {
    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public List<AlumnoResponse> list(@RequestParam(defaultValue = "false") boolean includeInactivos) {
        return alumnoService.list(includeInactivos);
    }

    @GetMapping("/{id}")
    public AlumnoResponse get(@PathVariable Long id) {
        return alumnoService.getById(id);
    }

    @PostMapping
    public AlumnoResponse create(@Valid @RequestBody AlumnoRequest request) {
        return alumnoService.create(request);
    }

    @PutMapping("/{id}")
    public AlumnoResponse update(@PathVariable Long id, @Valid @RequestBody AlumnoRequest request) {
        return alumnoService.update(id, request);
    }

    @PutMapping("/{id}")
    public AlumnoResponse update(@PathVariable Long id, @Valid @RequestBody AlumnoRequest request) {
        return alumnoService.update(id, request);
    }

    @PatchMapping("/{id}/activar")
    public void activar(@PathVariable Long id) {
        alumnoService.setActivo(id, true);
    }

    @PatchMapping("/{id}/inactivar")
    public void inactivar(@PathVariable Long id) {
        alumnoService.setActivo(id, false);
    }
}
