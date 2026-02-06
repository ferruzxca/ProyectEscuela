package com.proyectescuela.web.dto;

import com.proyectescuela.model.Grupo;

public class GrupoResponse {
    private Long id;
    private Long carreraId;
    private String carreraSigla;
    private Long turnoId;
    private String turnoSigla;
    private Long gradoId;
    private Integer gradoNumero;
    private Integer consecutivo;
    private String codigo;
    private boolean activo;

    public static GrupoResponse from(Grupo grupo) {
        GrupoResponse resp = new GrupoResponse();
        resp.setId(grupo.getId());
        resp.setCarreraId(grupo.getCarrera().getId());
        resp.setCarreraSigla(grupo.getCarrera().getSigla());
        resp.setTurnoId(grupo.getTurno().getId());
        resp.setTurnoSigla(grupo.getTurno().getSigla());
        resp.setGradoId(grupo.getGrado().getId());
        resp.setGradoNumero(grupo.getGrado().getNumero());
        resp.setConsecutivo(grupo.getConsecutivo());
        resp.setCodigo(grupo.getCodigo());
        resp.setActivo(grupo.isActivo());
        return resp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }

    public String getCarreraSigla() {
        return carreraSigla;
    }

    public void setCarreraSigla(String carreraSigla) {
        this.carreraSigla = carreraSigla;
    }

    public Long getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(Long turnoId) {
        this.turnoId = turnoId;
    }

    public String getTurnoSigla() {
        return turnoSigla;
    }

    public void setTurnoSigla(String turnoSigla) {
        this.turnoSigla = turnoSigla;
    }

    public Long getGradoId() {
        return gradoId;
    }

    public void setGradoId(Long gradoId) {
        this.gradoId = gradoId;
    }

    public Integer getGradoNumero() {
        return gradoNumero;
    }

    public void setGradoNumero(Integer gradoNumero) {
        this.gradoNumero = gradoNumero;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
