package org.example.controller;

import org.example.dto.LoginRequest;
import org.example.entity.Usuario;
import org.example.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return usuarioRepository.findByEmail(request.getEmail())
                .map(usuario -> {
                    // VALIDACIÓN SIMPLE: En producción usa BCrypt para comparar contraseñas
                    if (usuario.getPassword().equals(request.getPassword())) {
                        return ResponseEntity.ok(usuario); // Enviamos el usuario completo incluyendo el ROL
                    }
                    return ResponseEntity.status(401).body("Contraseña incorrecta");
                })
                .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
    }
}