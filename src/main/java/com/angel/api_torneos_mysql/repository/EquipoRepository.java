package com.angel.api_torneos_mysql.repository;

import com.angel.api_torneos_mysql.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipoRepository
        extends JpaRepository<Equipo, Long> {

    List<Equipo> findByTorneoId(Long torneoId);

    List<Equipo> findByCiudadIgnoreCase(String ciudad);
}