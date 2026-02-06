package com.proyectescuela.web.dto;

import com.proyectescuela.model.Grado;

public class GradoResponse {
    private Long id;
    private String nombre;
    private Integer numero;
    private boolean activo;

    public static GradoResponse from(Grado grado) {
        GradoResponse resp = new GradoResponse();
        resp.setId(grado.getId());
        resp.setNombre(grado.getNombre());
        resp.setNumero(grado.getNumero());
        resp.setActivo(grado.isActivo());
        return resp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
