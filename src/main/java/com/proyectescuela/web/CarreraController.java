package com.proyectescuela.web;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<CarreraResponse> list() {
        return carreraService.list();
    }

    @PostMapping
    public CarreraResponse create(@Valid @RequestBody CarreraRequest request) {
        return carreraService.create(request);
    }
}
