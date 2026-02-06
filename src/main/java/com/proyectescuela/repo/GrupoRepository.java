package com.proyectescuela.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectescuela.model.Grupo;

import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    List<Grupo> findAllByActivoTrue();
}
