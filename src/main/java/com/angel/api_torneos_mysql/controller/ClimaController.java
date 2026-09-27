package com.angel.api_torneos_mysql.controller;

import com.angel.api_torneos_mysql.dto.ClimaResponse;
import com.angel.api_torneos_mysql.service.ClimaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clima")
public class ClimaController {

    private final ClimaService climaService;

    public ClimaController(ClimaService climaService) {
        this.climaService = climaService;
    }

    @GetMapping
    public ResponseEntity<ClimaResponse> consultar(
            @RequestParam String ciudad
    ) {
        return ResponseEntity.ok(
                climaService.consultarClima(ciudad)
        );
    }
}