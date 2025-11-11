package com.datacash.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private Integer usuarioId;
    private String nombreUsuario;
    private String rol;
    private Integer mesId;
    private BigDecimal limiteGasto;
}