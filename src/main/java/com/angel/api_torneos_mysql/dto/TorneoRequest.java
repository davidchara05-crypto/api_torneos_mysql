package com.angel.api_torneos_mysql.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TorneoRequest(
        @NotBlank String nombre,
        @NotBlank String ciudad,
        @NotBlank String categoria,
        @Min(2) int numeroEquipos,
        @NotNull LocalDate fechaInicio
) {
}