package com.datacash.controller;

import com.datacash.dto.TransaccionRequest;
import com.datacash.model.Ingreso;
import com.datacash.model.Mes;
import com.datacash.repository.IngresoRepository;
import com.datacash.repository.MesRepository;
import com.datacash.service.TransaccionService; // <-- IMPORTANTE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {

    @Autowired
    private IngresoRepository ingresoRepository;
    @Autowired
    private MesRepository mesRepository;
    
    @Autowired
    private TransaccionService transaccionService; // <-- USAMOS EL SERVICIO

    @GetMapping
    public ResponseEntity<List<Ingreso>> getIngresosPorMes(@RequestParam Integer mesId) {
        Mes mes = mesRepository.findById(mesId).orElse(null);
        if (mes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingresoRepository.findByMesOrderByFechaTransaccionDesc(mes));
    }

    @PostMapping
    public ResponseEntity<Ingreso> addIngreso(@RequestBody TransaccionRequest request) {
        // La lógica compleja ahora está en el servicio
        Ingreso ingresoGuardado = transaccionService.crearIngreso(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingresoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngreso(@PathVariable Integer id) {
        if (!ingresoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ingresoRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("status", "success"));
    }
}