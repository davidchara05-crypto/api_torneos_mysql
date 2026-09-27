package com.angel.api_torneos_mysql.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipoRequest(
        @NotBlank String nombre,
        @NotBlank String ciudad,
        @NotBlank String entrenador,
        @NotNull Long torneoId
) {
}