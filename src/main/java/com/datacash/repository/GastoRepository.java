package com.datacash.repository;

import com.datacash.model.Gasto;
import com.datacash.model.Mes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Integer> {
    List<Gasto> findByMesOrderByFechaTransaccionDesc(Mes mes);
}