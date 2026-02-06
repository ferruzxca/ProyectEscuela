package com.proyectescuela.web;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectescuela.service.GradoService;
import com.proyectescuela.web.dto.GradoRequest;
import com.proyectescuela.web.dto.GradoResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/grados")
@Validated
public class GradoController {
    private final GradoService gradoService;

    public GradoController(GradoService gradoService) {
        this.gradoService = gradoService;
    }

    @GetMapping
    public List<GradoResponse> list() {
        return gradoService.list();
    }

    @PostMapping
    public GradoResponse create(@Valid @RequestBody GradoRequest request) {
        return gradoService.create(request);
    }
}
