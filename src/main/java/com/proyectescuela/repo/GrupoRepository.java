package com.proyectescuela.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectescuela.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
}
