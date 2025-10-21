package com.datacash.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;

    @Column(name = "nombre_usuario")
    private String nombre; // Simplificado

    @Column(name = "correo_electronico", unique = true)
    private String email; // Simplificado

    @Column(name = "contraseña_hash")
    private String password;
}