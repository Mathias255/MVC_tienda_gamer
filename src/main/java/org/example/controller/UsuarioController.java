package org.example.controller;

import org.example.dto.LoginRequest;
import org.example.dto.UsuarioRegistroDTO;
import org.example.dto.UsuarioRespuestaDTO;
import org.example.service.interfaces.UsuarioService; // 🚀 CORREGIDO: Importa desde .interfaces
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioRegistroDTO registroDTO) {
        System.out.println("\n=== 🚀 INTENTO DE REGISTRO EN CONTROLADOR ===");
        if (registroDTO == null) {
            return ResponseEntity.badRequest().body("El cuerpo no puede estar vacío.");
        }
        try {
            UsuarioRespuestaDTO guardado = usuarioService.registrar(registroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            System.err.println("❌ ERROR EN CONTROLADOR AL REGISTRAR:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el backend: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("\n=== 🔑 INTENTO DE LOGIN EN CONTROLADOR ===");
        if (loginRequest == null) {
            return ResponseEntity.badRequest().body("Credenciales vacías.");
        }
        try {
            UsuarioRespuestaDTO respuesta = usuarioService.login(loginRequest);
            if (respuesta != null) {
                return ResponseEntity.ok(respuesta);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o contraseña incorrectos.");
        } catch (Exception e) {
            System.err.println("❌ ERROR EN CONTROLADOR AL HACER LOGIN:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el login.");
        }
    }
}