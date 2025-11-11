package com.datacash.repository;

import com.datacash.model.Gasto;
import com.datacash.model.Mes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Integer> {
    List<Gasto> findByMesOrderByFechaTransaccionDesc(Mes mes);

    @Query("SELECT COALESCE(SUM(g.cantidad), 0) FROM Gasto g")
    BigDecimal sumTotalGastos();

    @Query("SELECT COALESCE(SUM(g.cantidad), 0) FROM Gasto g WHERE g.mes.usuario.id = :userId")
    BigDecimal sumGastosByUsuario(@Param("userId") Integer userId);

    @Query("SELECT COUNT(g) FROM Gasto g WHERE DATE(g.fechaTransaccion) = :fecha")
    Long countByFecha(@Param("fecha") LocalDate fecha);

    void deleteByMes_Usuario_Id(Integer userId);
}