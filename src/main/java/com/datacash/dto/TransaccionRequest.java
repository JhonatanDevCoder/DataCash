package com.datacash.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransaccionRequest {
    private Integer mesId;
    private Integer categoriaId;
    private BigDecimal cantidad;
    private LocalDate fechaTransaccion;
    private String descripcion;
}