package com.proyectescuela.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TurnoRequest {
    @NotBlank
    @Size(max = 50)
    private String nombre;

    @NotBlank
    @Size(max = 5)
    private String sigla;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
