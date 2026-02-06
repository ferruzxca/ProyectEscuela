package com.proyectescuela.web.dto;

import com.proyectescuela.model.Turno;

public class TurnoResponse {
    private Long id;
    private String nombre;
    private String sigla;
    private boolean activo;

    public static TurnoResponse from(Turno turno) {
        TurnoResponse resp = new TurnoResponse();
        resp.setId(turno.getId());
        resp.setNombre(turno.getNombre());
        resp.setSigla(turno.getSigla());
        resp.setActivo(turno.isActivo());
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
