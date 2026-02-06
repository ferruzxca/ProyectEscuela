package com.proyectescuela.web.dto;

import com.proyectescuela.model.Carrera;

public class CarreraResponse {
    private Long id;
    private String nombre;
    private String sigla;
    private boolean activo;

    public static CarreraResponse from(Carrera carrera) {
        CarreraResponse resp = new CarreraResponse();
        resp.setId(carrera.getId());
        resp.setNombre(carrera.getNombre());
        resp.setSigla(carrera.getSigla());
        resp.setActivo(carrera.isActivo());
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
