package com.datacash.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransaccionResponse {
    private Integer id;
    private BigDecimal cantidad;
    private LocalDate fechaTransaccion;
    private String descripcion;
    private String nombreCategoria;
    private String tipo; // "ingreso" o "gasto"
}