package com.clinica.servico_profissional.controller;

import com.clinica.servico_profissional.model.Profissional;
import com.clinica.servico_profissional.service.ProfissionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profissionais")
@RequiredArgsConstructor
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping
    public ResponseEntity<List<Profissional>> listar() {
        return ResponseEntity.ok(profissionalService.listarAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(profissionalService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("erro", e.getMessage()));
        }
    }
}