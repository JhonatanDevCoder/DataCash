package com.datacash.controller;

import com.datacash.model.Usuario;
import com.datacash.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> getEstadisticasGenerales() {
        return ResponseEntity.ok(adminService.getEstadisticasGenerales());
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Map<String, Object>>> getUsuarios() {
        return ResponseEntity.ok(adminService.getListaUsuarios());
    }

    @GetMapping("/usuario/{id}/detalles")
    public ResponseEntity<Map<String, Object>> getDetallesUsuario(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.getDetallesUsuario(id));
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        adminService.eliminarUsuario(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/actividad-usuarios")
    public ResponseEntity<Map<String, Object>> getActividadUsuarios() {
        return ResponseEntity.ok(adminService.getActividadUsuarios());
    }

    @GetMapping("/distribucion-transacciones")
    public ResponseEntity<Map<String, Object>> getDistribucionTransacciones() {
        return ResponseEntity.ok(adminService.getDistribucionTransacciones());
    }
}