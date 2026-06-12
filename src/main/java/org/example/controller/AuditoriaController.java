package org.example.controller;

import org.example.entity.AuditoriaAcceso;
import org.example.repository.AuditoriaAccesoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@CrossOrigin(origins = "*") // Permite la conexión limpia con Angular
public class AuditoriaController {

    @Autowired
    private AuditoriaAccesoRepository auditoriaAccesoRepository;

    // 1. Obtener todos los registros de auditoría de acceso (GET /api/auditoria)
    @GetMapping
    public ResponseEntity<List<AuditoriaAcceso>> obtenerTodasLasAuditorias() {
        List<AuditoriaAcceso> auditorias = auditoriaAccesoRepository.findAll();
        if (auditorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(auditorias);
    }

    // 2. Obtener auditoría por un ID específico (GET /api/auditoria/{id})
    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaAcceso> obtenerAuditoriaPorId(@PathVariable Long id) {
        return auditoriaAccesoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Crear un nuevo registro de auditoría (POST /api/auditoria)
    @PostMapping
    public ResponseEntity<AuditoriaAcceso> crearAuditoria(@RequestBody AuditoriaAcceso auditoriaAcceso) {
        try {
            AuditoriaAcceso nuevaAuditoria = auditoriaAccesoRepository.save(auditoriaAcceso);
            return ResponseEntity.ok(nuevaAuditoria);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}