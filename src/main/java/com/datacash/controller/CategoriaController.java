package com.datacash.controller;

import com.datacash.model.Categoria;
import com.datacash.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Categoria>> getCategoriasPorTipo(@RequestParam String tipo) {
        if (!tipo.equals("ingreso") && !tipo.equals("gasto")) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(categoriaRepository.findByTipo(tipo));
    }
}