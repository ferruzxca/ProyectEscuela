package com.proyectescuela.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectescuela.model.Grado;

import java.util.List;

public interface GradoRepository extends JpaRepository<Grado, Long> {
    List<Grado> findAllByActivoTrue();
    boolean existsByNumero(Integer numero);
    boolean existsByNumeroAndIdNot(Integer numero, Long id);
}
