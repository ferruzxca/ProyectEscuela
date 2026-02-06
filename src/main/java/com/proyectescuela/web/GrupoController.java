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
    public List<GrupoResponse> list(@RequestParam(defaultValue = "false") boolean includeInactivos) {
        return grupoService.list(includeInactivos);
    }

    @PostMapping
    public GrupoResponse create(@Valid @RequestBody GrupoRequest request) {
        return grupoService.create(request);
    }

    @PutMapping("/{id}")
    public GrupoResponse update(@PathVariable Long id, @Valid @RequestBody GrupoRequest request) {
        return grupoService.update(id, request);
    }

    @PatchMapping("/{id}/activar")
    public void activar(@PathVariable Long id) {
        grupoService.setActivo(id, true);
    }

    @PatchMapping("/{id}/inactivar")
    public void inactivar(@PathVariable Long id) {
        grupoService.setActivo(id, false);
    }
}
