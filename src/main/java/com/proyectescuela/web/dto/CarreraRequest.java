package com.proyectescuela.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CarreraRequest {
    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotBlank
    @Size(max = 10)
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
