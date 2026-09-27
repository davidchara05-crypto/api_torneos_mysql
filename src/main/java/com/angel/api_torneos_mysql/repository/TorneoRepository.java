package com.angel.api_torneos_mysql.repository;

import com.angel.api_torneos_mysql.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TorneoRepository
        extends JpaRepository<Torneo, Long> {

    List<Torneo> findByCiudadIgnoreCase(String ciudad);

    List<Torneo> findByCategoriaIgnoreCase(String categoria);
}