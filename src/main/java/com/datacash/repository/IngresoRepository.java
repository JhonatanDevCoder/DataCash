package com.datacash.repository;

import com.datacash.model.Ingreso;
import com.datacash.model.Mes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IngresoRepository extends JpaRepository<Ingreso, Integer> {
    List<Ingreso> findByMesOrderByFechaTransaccionDesc(Mes mes);

    @Query("SELECT COALESCE(SUM(i.cantidad), 0) FROM Ingreso i")
    BigDecimal sumTotalIngresos();

    @Query("SELECT COALESCE(SUM(i.cantidad), 0) FROM Ingreso i WHERE i.mes.usuario.id = :userId")
    BigDecimal sumIngresosByUsuario(@Param("userId") Integer userId);

    @Query("SELECT COUNT(i) FROM Ingreso i WHERE DATE(i.fechaTransaccion) = :fecha")
    Long countByFecha(@Param("fecha") LocalDate fecha);

    void deleteByMes_Usuario_Id(Integer userId);
}