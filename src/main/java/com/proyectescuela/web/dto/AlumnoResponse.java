package com.proyectescuela.web.dto;

import com.proyectescuela.model.Alumno;

public class AlumnoResponse {
    private Long id;
    private String matricula;
    private String nombre;
    private String apellidos;
    private Long grupoId;
    private String grupoCodigo;
    private boolean activo;

    public static AlumnoResponse from(Alumno alumno) {
        AlumnoResponse resp = new AlumnoResponse();
        resp.setId(alumno.getId());
        resp.setMatricula(alumno.getMatricula());
        resp.setNombre(alumno.getNombre());
        resp.setApellidos(alumno.getApellidos());
        resp.setGrupoId(alumno.getGrupo().getId());
        resp.setGrupoCodigo(alumno.getGrupo().getCodigo());
        resp.setActivo(alumno.isActivo());
        return resp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public String getGrupoCodigo() {
        return grupoCodigo;
    }

    public void setGrupoCodigo(String grupoCodigo) {
        this.grupoCodigo = grupoCodigo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
