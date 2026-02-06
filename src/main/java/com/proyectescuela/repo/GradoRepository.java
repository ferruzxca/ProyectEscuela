package com.proyectescuela.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectescuela.model.Grado;

public interface GradoRepository extends JpaRepository<Grado, Long> {
}
