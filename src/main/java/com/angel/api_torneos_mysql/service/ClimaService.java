package com.angel.api_torneos_mysql.service;

import com.angel.api_torneos_mysql.dto.ClimaResponse;
import com.angel.api_torneos_mysql.exception.ExternalApiException;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
public class ClimaService {

    private static final Logger logger =
            LoggerFactory.getLogger(ClimaService.class);

    private final RestClient restClient;

    private final Counter consultasClima;

    public ClimaService(
            RestClient restClient,
            MeterRegistry meterRegistry) {

        this.restClient = restClient;

        this.consultasClima = Counter.builder(
                        "clima.consultas"
                )
                .description(
                        "Cantidad de consultas realizadas a la API del clima"
                )
                .register(meterRegistry);
    }

    public ClimaResponse consultarClima(String ciudad) {

        logger.info(
                "Consultando clima para la ciudad {}",
                ciudad
        );

        try {

            GeocodingResponse geocoding =
                    restClient.get()
                            .uri(uriBuilder ->
                                    uriBuilder
                                            .scheme("https")
                                            .host("geocoding-api.open-meteo.com")
                                            .path("/v1/search")
                                            .queryParam("name", ciudad)
                                            .queryParam("count", 1)
                                            .queryParam("language", "es")
                                            .queryParam("format", "json")
                                            .build()
                            )
                            .retrieve()
                            .body(GeocodingResponse.class);

            if (geocoding == null ||
                    geocoding.results() == null ||
                    geocoding.results().isEmpty()) {

                logger.warn(
                        "No se encontró la ciudad {}",
                        ciudad
                );

                throw new ExternalApiException(
                        "No se encontró la ciudad: " + ciudad
                );
            }

            Location location =
                    geocoding.results().get(0);

            WeatherResponse weather =
                    restClient.get()
                            .uri(uriBuilder ->
                                    uriBuilder
                                            .scheme("https")
                                            .host("api.open-meteo.com")
                                            .path("/v1/forecast")
                                            .queryParam(
                                                    "latitude",
                                                    location.latitude()
                                            )
                                            .queryParam(
                                                    "longitude",
                                                    location.longitude()
                                            )
                                            .queryParam(
                                                    "current",
                                                    "temperature_2m,weather_code,wind_speed_10m"
                                            )
                                            .queryParam(
                                                    "timezone",
                                                    "auto"
                                            )
                                            .build()
                            )
                            .retrieve()
                            .body(WeatherResponse.class);

            if (weather == null ||
                    weather.current() == null) {

                throw new ExternalApiException(
                        "La API del clima no devolvió información"
                );
            }

            consultasClima.increment();

            logger.info(
                    "Clima consultado correctamente para {}",
                    ciudad
            );

            return new ClimaResponse(
                    location.name(),
                    location.country(),
                    location.latitude(),
                    location.longitude(),
                    weather.current().temperature(),
                    weather.current().weatherCode(),
                    weather.current().windSpeed()
            );

        } catch (RestClientException e) {

            logger.error(
                    "Error al consumir la API externa de clima",
                    e
            );

            throw new ExternalApiException(
                    "No fue posible consultar el servicio externo del clima",
                    e
            );
        }
    }

    public record GeocodingResponse(
            List<Location> results
    ) {
    }

    public record Location(
            String name,
            Double latitude,
            Double longitude,
            String country
    ) {
    }

    public record WeatherResponse(
            Current current
    ) {
    }

    public record Current(

            @com.fasterxml.jackson.annotation.JsonProperty(
                    "temperature_2m"
            )
            Double temperature,

            @com.fasterxml.jackson.annotation.JsonProperty(
                    "weather_code"
            )
            Integer weatherCode,

            @com.fasterxml.jackson.annotation.JsonProperty(
                    "wind_speed_10m"
            )
            Double windSpeed
    ) {
    }
}