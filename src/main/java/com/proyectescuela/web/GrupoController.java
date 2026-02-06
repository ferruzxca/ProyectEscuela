package com.proyectescuela.web;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectescuela.service.GrupoService;
import com.proyectescuela.web.dto.GrupoRequest;
import com.proyectescuela.web.dto.GrupoResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/grupos")
@Validated
public class GrupoController {
    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping
    public List<GrupoResponse> list() {
        return grupoService.list();
    }

    @PostMapping
    public GrupoResponse create(@Valid @RequestBody GrupoRequest request) {
        return grupoService.create(request);
    }
}
