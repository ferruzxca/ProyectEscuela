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

import com.proyectescuela.service.CarreraService;
import com.proyectescuela.web.dto.CarreraRequest;
import com.proyectescuela.web.dto.CarreraResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/carreras")
@Validated
public class CarreraController {
    private final CarreraService carreraService;

    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @GetMapping
    public List<CarreraResponse> list(@RequestParam(defaultValue = "false") boolean includeInactivos) {
        return carreraService.list(includeInactivos);
    }

    @PostMapping
    public CarreraResponse create(@Valid @RequestBody CarreraRequest request) {
        return carreraService.create(request);
    }

    @PutMapping("/{id}")
    public CarreraResponse update(@PathVariable Long id, @Valid @RequestBody CarreraRequest request) {
        return carreraService.update(id, request);
    }

    @PatchMapping("/{id}/activar")
    public void activar(@PathVariable Long id) {
        carreraService.setActivo(id, true);
    }

    @PatchMapping("/{id}/inactivar")
    public void inactivar(@PathVariable Long id) {
        carreraService.setActivo(id, false);
    }
}
