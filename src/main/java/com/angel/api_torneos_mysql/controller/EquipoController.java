package com.angel.api_torneos_mysql.controller;

import com.angel.api_torneos_mysql.dto.EquipoRequest;
import com.angel.api_torneos_mysql.model.Equipo;
import com.angel.api_torneos_mysql.service.EquipoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    // POST - Crear equipo
    @PostMapping
    public ResponseEntity<Equipo> crear(
            @Valid @RequestBody EquipoRequest request
    ) {

        Equipo equipo = equipoService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(equipo);
    }

    // GET - Obtener todos los equipos
    @GetMapping
    public ResponseEntity<List<Equipo>> obtenerTodos() {

        return ResponseEntity.ok(
                equipoService.obtenerTodos()
        );
    }

    // GET - Obtener equipo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtenerPorId(
            @PathVariable Long id
    ) {

        Equipo equipo = equipoService.obtenerPorId(id);

        return ResponseEntity.ok(equipo);
    }

    // GET - Obtener equipos de un torneo
    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<Equipo>> obtenerPorTorneo(
            @PathVariable Long torneoId
    ) {

        List<Equipo> equipos =
                equipoService.obtenerPorTorneo(torneoId);

        return ResponseEntity.ok(equipos);
    }

    // PUT - Actualizar equipo
    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EquipoRequest request
    ) {

        Equipo equipo =
                equipoService.actualizar(id, request);

        return ResponseEntity.ok(equipo);
    }

    // DELETE - Eliminar equipo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        equipoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}