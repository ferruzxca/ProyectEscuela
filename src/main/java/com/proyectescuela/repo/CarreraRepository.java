package com.proyectescuela.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectescuela.model.Carrera;

import java.util.List;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    List<Carrera> findAllByActivoTrue();
    boolean existsBySigla(String sigla);
    boolean existsBySiglaAndIdNot(String sigla, Long id);
}
