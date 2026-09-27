package com.angel.api_torneos_mysql.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "torneos")
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private int numeroEquipos;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @OneToMany(
            mappedBy = "torneo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Equipo> equipos = new ArrayList<>();

    public Torneo() {
    }

    public Torneo(
            Long id,
            String nombre,
            String ciudad,
            String categoria,
            int numeroEquipos,
            LocalDate fechaInicio
    ) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.categoria = categoria;
        this.numeroEquipos = numeroEquipos;
        this.fechaInicio = fechaInicio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getNumeroEquipos() {
        return numeroEquipos;
    }

    public void setNumeroEquipos(int numeroEquipos) {
        this.numeroEquipos = numeroEquipos;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }
}