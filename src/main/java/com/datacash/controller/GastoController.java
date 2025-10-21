package com.datacash.controller;

import com.datacash.dto.TransaccionRequest;
import com.datacash.model.Gasto;
import com.datacash.model.Mes;
import com.datacash.repository.GastoRepository;
import com.datacash.repository.MesRepository;
import com.datacash.service.TransaccionService; // <-- IMPORTANTE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    @Autowired
    private GastoRepository gastoRepository;
    @Autowired
    private MesRepository mesRepository;

    @Autowired
    private TransaccionService transaccionService; // <-- USAMOS EL SERVICIO

    @GetMapping
    public ResponseEntity<List<Gasto>> getGastosPorMes(@RequestParam Integer mesId) {
        Mes mes = mesRepository.findById(mesId).orElse(null);
        if (mes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gastoRepository.findByMesOrderByFechaTransaccionDesc(mes));
    }

    @PostMapping
    public ResponseEntity<Gasto> addGasto(@RequestBody TransaccionRequest request) {
        // La lógica compleja ahora está en el servicio
        Gasto gastoGuardado = transaccionService.crearGasto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(gastoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGasto(@PathVariable Integer id) {
        if (!gastoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        gastoRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("status", "success"));
    }
}