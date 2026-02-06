package com.proyectescuela.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectescuela.model.Carrera;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {
}
