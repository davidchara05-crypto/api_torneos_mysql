package com.angel.api_torneos_mysql.health;

import com.angel.api_torneos_mysql.repository.TorneoRepository;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("torneos")
public class TorneosHealthIndicator
        implements HealthIndicator {

    private final TorneoRepository torneoRepository;

    public TorneosHealthIndicator(
            TorneoRepository torneoRepository) {

        this.torneoRepository = torneoRepository;
    }

    @Override
    public Health health() {

        try {

            long cantidad =
                    torneoRepository.count();

            return Health.up()
                    .withDetail(
                            "servicio",
                            "Torneos"
                    )
                    .withDetail(
                            "cantidadTorneos",
                            cantidad
                    )
                    .build();

        } catch (Exception e) {

            return Health.down()
                    .withDetail(
                            "error",
                            "No se pudo consultar la base de datos"
                    )
                    .withException(e)
                    .build();
        }
    }
}