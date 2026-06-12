package org.example.controller;

import org.example.dto.LoginRequest; // Cambia a tu DTO de login si aplica, o usa un Map
import org.example.dto.LoginResponse;
import org.example.dto.UsuarioRegistroDTO;
import org.example.entity.Usuario;
import org.example.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");

            LoginResponse response = usuarioService.autenticar(email, password);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody UsuarioRegistroDTO usuarioDTO) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error en el registro: " + e.getMessage()));
        }
    }
}