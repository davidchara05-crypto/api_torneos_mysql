package com.angel.api_torneos_mysql.service;

import com.angel.api_torneos_mysql.dto.EquipoRequest;
import com.angel.api_torneos_mysql.model.Equipo;
import com.angel.api_torneos_mysql.model.Torneo;
import com.angel.api_torneos_mysql.repository.EquipoRepository;
import com.angel.api_torneos_mysql.repository.TorneoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {

    private static final Logger logger =
            LoggerFactory.getLogger(EquipoService.class);

    private final EquipoRepository equipoRepository;
    private final TorneoRepository torneoRepository;

    public EquipoService(
            EquipoRepository equipoRepository,
            TorneoRepository torneoRepository
    ) {
        this.equipoRepository = equipoRepository;
        this.torneoRepository = torneoRepository;
    }

    public Equipo crear(EquipoRequest request) {

        logger.info("Creando equipo: {}", request.nombre());

        Torneo torneo = torneoRepository.findById(request.torneoId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe el torneo con ID: " + request.torneoId()
                        )
                );

        Equipo equipo = new Equipo();

        equipo.setNombre(request.nombre());
        equipo.setCiudad(request.ciudad());
        equipo.setEntrenador(request.entrenador());
        equipo.setTorneo(torneo);

        return equipoRepository.save(equipo);
    }

    public List<Equipo> obtenerTodos() {
        logger.info("Consultando todos los equipos");
        return equipoRepository.findAll();
    }

    public Equipo obtenerPorId(Long id) {

        return equipoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe el equipo con ID: " + id
                        )
                );
    }

    public List<Equipo> obtenerPorTorneo(Long torneoId) {

        logger.info("Consultando equipos del torneo {}", torneoId);

        return equipoRepository.findByTorneoId(torneoId);
    }

    public Equipo actualizar(Long id, EquipoRequest request) {

        Equipo equipo = obtenerPorId(id);

        Torneo torneo = torneoRepository.findById(request.torneoId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe el torneo con ID: " + request.torneoId()
                        )
                );

        equipo.setNombre(request.nombre());
        equipo.setCiudad(request.ciudad());
        equipo.setEntrenador(request.entrenador());
        equipo.setTorneo(torneo);

        logger.info("Actualizando equipo {}", id);

        return equipoRepository.save(equipo);
    }

    public void eliminar(Long id) {

        Equipo equipo = obtenerPorId(id);

        equipoRepository.delete(equipo);

        logger.info("Equipo {} eliminado", id);
    }
}