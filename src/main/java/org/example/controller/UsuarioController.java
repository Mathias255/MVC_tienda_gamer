package org.example.controller;

import org.example.entity.Usuario;
import org.example.dto.UsuarioRegistroDTO;
import org.example.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Permite que Angular se conecte sin problemas de CORS
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return ResponseEntity.ok(usuario);
    }

    //  Esto escucha en: POST http://localhost:8080/api/usuarios
    @PostMapping
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody UsuarioRegistroDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(usuarioDTO));
    }

    // 🔄 SOPORTE EXTRA: Esto escucha en: POST http://localhost:8080/api/usuarios/registrar
    // Por si tu Angular le está pegando a esta URL específica
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuarioAlternativo(@RequestBody UsuarioRegistroDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(usuarioDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody UsuarioRegistroDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.actualizar(id, usuarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}