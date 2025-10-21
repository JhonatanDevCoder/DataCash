package com.datacash.service;

import com.datacash.dto.TransaccionRequest;
import com.datacash.model.*;
import com.datacash.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransaccionService {

    @Autowired
    private IngresoRepository ingresoRepository;
    @Autowired
    private GastoRepository gastoRepository;
    @Autowired
    private MesRepository mesRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Ingreso crearIngreso(TransaccionRequest request) {
        Mes mes = mesRepository.findById(request.getMesId())
            .orElseThrow(() -> new RuntimeException("Error: Mes no encontrado con id " + request.getMesId()));

        Categoria categoria = null;
        if (request.getCategoriaId() != null && request.getCategoriaId() > 0) {
            categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Error: Categoría no encontrada"));
        }

        Ingreso nuevoIngreso = new Ingreso();
        nuevoIngreso.setMes(mes);
        nuevoIngreso.setCategoria(categoria);
        nuevoIngreso.setCantidad(request.getCantidad());
        nuevoIngreso.setFechaTransaccion(request.getFechaTransaccion());
        nuevoIngreso.setDescripcion(request.getDescripcion());

        return ingresoRepository.save(nuevoIngreso);
    }

    public Gasto crearGasto(TransaccionRequest request) {
        Mes mes = mesRepository.findById(request.getMesId())
            .orElseThrow(() -> new RuntimeException("Error: Mes no encontrado con id " + request.getMesId()));

        Categoria categoria = null;
        if (request.getCategoriaId() != null && request.getCategoriaId() > 0) {
            categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Error: Categoría no encontrada"));
        }

        Gasto nuevoGasto = new Gasto();
        nuevoGasto.setMes(mes);
        nuevoGasto.setCategoria(categoria);
        nuevoGasto.setCantidad(request.getCantidad());
        nuevoGasto.setFechaTransaccion(request.getFechaTransaccion());
        nuevoGasto.setDescripcion(request.getDescripcion());

        return gastoRepository.save(nuevoGasto);
    }
}