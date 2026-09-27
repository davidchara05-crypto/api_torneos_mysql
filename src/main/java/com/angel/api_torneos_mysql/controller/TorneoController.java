package com.angel.api_torneos_mysql.controller;

import com.angel.api_torneos_mysql.dto.TorneoRequest;
import com.angel.api_torneos_mysql.model.Torneo;
import com.angel.api_torneos_mysql.service.TorneoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/torneos")
public class TorneoController {

    private final TorneoService torneoService;

    public TorneoController(TorneoService torneoService) {
        this.torneoService = torneoService;
    }

    @PostMapping
    public ResponseEntity<Torneo> crear(
            @Valid @RequestBody TorneoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(torneoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<Torneo>> obtenerTodos() {

        return ResponseEntity.ok(
                torneoService.obtenerTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Torneo> obtenerPorId(
            @PathVariable Long id) {

        return torneoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/buscar/ciudad")
    public ResponseEntity<List<Torneo>> buscarCiudad(
            @RequestParam String ciudad) {

        return ResponseEntity.ok(
                torneoService.buscarPorCiudad(ciudad)
        );
    }

    @GetMapping("/buscar/categoria")
    public ResponseEntity<List<Torneo>> buscarCategoria(
            @RequestParam String categoria) {

        return ResponseEntity.ok(
                torneoService.buscarPorCategoria(categoria)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Torneo> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TorneoRequest request) {

        return torneoService.actualizar(id, request)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        if (!torneoService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}