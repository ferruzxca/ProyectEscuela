package com.proyectescuela.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyectescuela.model.Grupo;

import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    List<Grupo> findAllByActivoTrue();

    @Query("select coalesce(max(g.consecutivo), 0) from Grupo g " +
           "where g.carrera.id = :carreraId and g.turno.id = :turnoId and g.grado.id = :gradoId")
    Integer findMaxConsecutivo(@Param("carreraId") Long carreraId,
                               @Param("turnoId") Long turnoId,
                               @Param("gradoId") Long gradoId);

    boolean existsByCarreraIdAndTurnoIdAndGradoIdAndConsecutivo(Long carreraId,
                                                                Long turnoId,
                                                                Long gradoId,
                                                                Integer consecutivo);
}
