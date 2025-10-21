package com.datacash.repository;

import com.datacash.model.Ingreso;
import com.datacash.model.Mes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IngresoRepository extends JpaRepository<Ingreso, Integer> {
    List<Ingreso> findByMesOrderByFechaTransaccionDesc(Mes mes);
}