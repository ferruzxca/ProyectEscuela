package com.proyectescuela.web;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.proyectescuela.service.TurnoService;
import com.proyectescuela.web.dto.TurnoRequest;
import com.proyectescuela.web.dto.TurnoResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/turnos")
@Validated
public class TurnoController {
    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping
    public List<TurnoResponse> list(@RequestParam(defaultValue = "false") boolean includeInactivos) {
        return turnoService.list(includeInactivos);
    }

    @PostMapping
    public TurnoResponse create(@Valid @RequestBody TurnoRequest request) {
        return turnoService.create(request);
    }

    @PatchMapping("/{id}/activar")
    public void activar(@PathVariable Long id) {
        turnoService.setActivo(id, true);
    }

    @PatchMapping("/{id}/inactivar")
    public void inactivar(@PathVariable Long id) {
        turnoService.setActivo(id, false);
    }
}
