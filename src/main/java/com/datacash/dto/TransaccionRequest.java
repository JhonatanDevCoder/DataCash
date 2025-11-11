package com.datacash.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransaccionRequest {
    private Integer mesId;
    private Integer categoriaId;
    private BigDecimal cantidad;
    private LocalDate fechaTransaccion;
    private String descripcion;

    // Getters
    public Integer getMesId() {
        return mesId;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public LocalDate getFechaTransaccion() {
        return fechaTransaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setters
    public void setMesId(Integer mesId) {
        this.mesId = mesId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public void setFechaTransaccion(LocalDate fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}