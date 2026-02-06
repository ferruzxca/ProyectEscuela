package com.proyectescuela.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectescuela.model.Turno;

import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
    List<Turno> findAllByActivoTrue();
}
