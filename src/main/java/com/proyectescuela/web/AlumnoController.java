package com.proyectescuela.web;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<AlumnoResponse> list() {
        return alumnoService.list();
    }

    @PostMapping
    public AlumnoResponse create(@Valid @RequestBody AlumnoRequest request) {
        return alumnoService.create(request);
    }
}
