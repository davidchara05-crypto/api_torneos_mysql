package com.angel.api_torneos_mysql.service;

import com.angel.api_torneos_mysql.dto.TorneoRequest;
import com.angel.api_torneos_mysql.model.Torneo;
import com.angel.api_torneos_mysql.repository.TorneoRepository;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TorneoService {

    private static final Logger logger =
            LoggerFactory.getLogger(TorneoService.class);

    private final TorneoRepository torneoRepository;

    private final Counter torneosCreados;

    private final Counter torneosConsultados;

    public TorneoService(
            TorneoRepository torneoRepository,
            MeterRegistry meterRegistry) {

        this.torneoRepository = torneoRepository;

        this.torneosCreados = Counter.builder("torneos.creados")
                .description("Cantidad de torneos creados")
                .register(meterRegistry);

        this.torneosConsultados = Counter.builder("torneos.consultados")
                .description("Cantidad de consultas de torneos")
                .register(meterRegistry);
    }

    public Torneo crear(TorneoRequest request) {

        logger.info(
                "Creando torneo: {} en ciudad {}",
                request.nombre(),
                request.ciudad()
        );

        Torneo torneo = new Torneo();

        torneo.setNombre(request.nombre());
        torneo.setCiudad(request.ciudad());
        torneo.setCategoria(request.categoria());
        torneo.setNumeroEquipos(request.numeroEquipos());
        torneo.setFechaInicio(request.fechaInicio());

        Torneo guardado = torneoRepository.save(torneo);

        torneosCreados.increment();

        logger.info(
                "Torneo creado correctamente con ID {}",
                guardado.getId()
        );

        return guardado;
    }

    public List<Torneo> obtenerTodos() {

        logger.info("Consultando todos los torneos");

        torneosConsultados.increment();

        return torneoRepository.findAll();
    }

    public Optional<Torneo> obtenerPorId(Long id) {

        logger.info("Consultando torneo con ID {}", id);

        torneosConsultados.increment();

        return torneoRepository.findById(id);
    }

    public Optional<Torneo> actualizar(
            Long id,
            TorneoRequest request) {

        logger.info("Actualizando torneo con ID {}", id);

        return torneoRepository.findById(id)
                .map(torneo -> {

                    torneo.setNombre(request.nombre());
                    torneo.setCiudad(request.ciudad());
                    torneo.setCategoria(request.categoria());
                    torneo.setNumeroEquipos(request.numeroEquipos());
                    torneo.setFechaInicio(request.fechaInicio());

                    return torneoRepository.save(torneo);
                });
    }

    public boolean eliminar(Long id) {

        logger.warn("Solicitud para eliminar torneo ID {}", id);

        if (!torneoRepository.existsById(id)) {
            logger.error(
                    "No se encontró el torneo con ID {}",
                    id
            );

            return false;
        }

        torneoRepository.deleteById(id);

        logger.info(
                "Torneo con ID {} eliminado correctamente",
                id
        );

        return true;
    }

    public List<Torneo> buscarPorCiudad(String ciudad) {

        logger.info(
                "Buscando torneos de la ciudad {}",
                ciudad
        );

        torneosConsultados.increment();

        return torneoRepository.findByCiudadIgnoreCase(ciudad);
    }

    public List<Torneo> buscarPorCategoria(String categoria) {

        logger.info(
                "Buscando torneos de categoría {}",
                categoria
        );

        torneosConsultados.increment();

        return torneoRepository.findByCategoriaIgnoreCase(categoria);
    }
}