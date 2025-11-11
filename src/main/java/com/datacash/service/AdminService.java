package com.datacash.service;

import com.datacash.model.Usuario;
import com.datacash.model.Gasto;
import com.datacash.model.Ingreso;
import com.datacash.repository.UsuarioRepository;
import com.datacash.repository.GastoRepository;
import com.datacash.repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private IngresoRepository ingresoRepository;

    public Map<String, Object> getEstadisticasGenerales() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalUsuarios = usuarioRepository.count();
        long totalGastos = gastoRepository.count();
        long totalIngresos = ingresoRepository.count();
        
        BigDecimal movimientoTotal = ingresoRepository.sumTotalIngresos()
            .subtract(gastoRepository.sumTotalGastos());

        stats.put("totalUsuarios", totalUsuarios);
        stats.put("totalTransacciones", totalGastos + totalIngresos);
        stats.put("movimientoTotal", movimientoTotal);
        
        return stats;
    }

    public List<Map<String, Object>> getListaUsuarios() {
        return usuarioRepository.findAll().stream()
            .map(usuario -> {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", usuario.getId());
                userMap.put("nombre", usuario.getNombre());
                userMap.put("email", usuario.getEmail());
                userMap.put("balanceTotal", calcularBalanceUsuario(usuario.getId()));
                return userMap;
            })
            .collect(Collectors.toList());
    }

    public Map<String, Object> getDetallesUsuario(Integer userId) {
        Usuario usuario = usuarioRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Map<String, Object> detalles = new HashMap<>();
        detalles.put("nombre", usuario.getNombre());
        detalles.put("totalIngresos", ingresoRepository.sumIngresosByUsuario(userId));
        detalles.put("totalGastos", gastoRepository.sumGastosByUsuario(userId));
        detalles.put("balanceTotal", calcularBalanceUsuario(userId));
        detalles.put("categoriasPrincipales", getCategoriasPrincipales(userId));

        return detalles;
    }

    @Transactional
    public void eliminarUsuario(Integer userId) {
        // Primero eliminar todas las transacciones asociadas
        // los repositorios definen el método deleteByMes_Usuario_Id para borrar por usuario
        gastoRepository.deleteByMes_Usuario_Id(userId);
        ingresoRepository.deleteByMes_Usuario_Id(userId);
        // Finalmente eliminar el usuario
        usuarioRepository.deleteById(userId);
    }

    public Map<String, Object> getActividadUsuarios() {
        LocalDate fechaInicio = LocalDate.now().minusDays(30);
        List<LocalDate> fechas = new ArrayList<>();
        List<Long> transacciones = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            LocalDate fecha = fechaInicio.plusDays(i);
            fechas.add(fecha);
            long totalTransacciones = gastoRepository.countByFecha(fecha) + 
                                    ingresoRepository.countByFecha(fecha);
            transacciones.add(totalTransacciones);
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("fechas", fechas);
        resultado.put("transacciones", transacciones);
        return resultado;
    }

    public Map<String, Object> getDistribucionTransacciones() {
        BigDecimal totalIngresos = ingresoRepository.sumTotalIngresos();
        BigDecimal totalGastos = gastoRepository.sumTotalGastos();

        Map<String, Object> distribucion = new HashMap<>();
        distribucion.put("ingresos", totalIngresos);
        distribucion.put("gastos", totalGastos);
        return distribucion;
    }

    private BigDecimal calcularBalanceUsuario(Integer userId) {
        BigDecimal ingresos = ingresoRepository.sumIngresosByUsuario(userId);
        BigDecimal gastos = gastoRepository.sumGastosByUsuario(userId);
        return ingresos.subtract(gastos);
    }

    private List<Map<String, Object>> getCategoriasPrincipales(Integer userId) {
        // Implementar lógica para obtener las categorías más usadas por el usuario
        // Esto dependerá de tu estructura de datos específica
        return new ArrayList<>();
    }
}