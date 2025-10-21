package com.datacash.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "gastos")
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gasto_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "mes_id", nullable = false)
    private Mes mes;

    @ManyToOne
    @JoinColumn(name = "categoria_id") // Puede ser nulo
    private Categoria categoria;

    private BigDecimal cantidad;

    @Column(name = "fecha_transaccion")
    private LocalDate fechaTransaccion;

    private String descripcion;
}