package com.datacash.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "meses")
public class Mes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mes_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private int anio;

    private int mes;

    @Column(name = "limite_gasto")
    private BigDecimal limiteGasto;

    // Getters
    public Integer getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public int getAnio() {
        return anio;
    }

    public int getMes() {
        return mes;
    }

    public BigDecimal getLimiteGasto() {
        return limiteGasto;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setLimiteGasto(BigDecimal limiteGasto) {
        this.limiteGasto = limiteGasto;
    }
}