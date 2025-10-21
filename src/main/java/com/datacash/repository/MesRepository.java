package com.datacash.repository;

import com.datacash.model.Mes;
import com.datacash.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MesRepository extends JpaRepository<Mes, Integer> {
    Optional<Mes> findByUsuarioAndAnioAndMes(Usuario usuario, int anio, int mes); // Cambiado a findByUsuarioAndAnioAndMes
}