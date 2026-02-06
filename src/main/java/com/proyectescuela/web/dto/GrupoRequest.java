package com.proyectescuela.web.dto;

import jakarta.validation.constraints.NotNull;

public class GrupoRequest {
    @NotNull
    private Long carreraId;

    @NotNull
    private Long turnoId;

    @NotNull
    private Long gradoId;

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }

    public Long getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(Long turnoId) {
        this.turnoId = turnoId;
    }

    public Long getGradoId() {
        return gradoId;
    }

    public void setGradoId(Long gradoId) {
        this.gradoId = gradoId;
    }
}
