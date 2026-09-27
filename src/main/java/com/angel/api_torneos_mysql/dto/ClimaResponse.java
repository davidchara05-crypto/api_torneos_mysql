package com.angel.api_torneos_mysql.dto;

public record ClimaResponse(
        String ciudad,
        String pais,
        Double latitud,
        Double longitud,
        Double temperatura,
        Integer codigoClima,
        Double velocidadViento
) {
}