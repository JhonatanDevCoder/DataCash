package com.datacash.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
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

    private int anio; // Simplificado a minúscula

    private int mes;

    @Column(name = "limite_gasto")
    private BigDecimal limiteGasto;
}