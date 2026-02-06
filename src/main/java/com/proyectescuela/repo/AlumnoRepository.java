package com.proyectescuela.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectescuela.model.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}
