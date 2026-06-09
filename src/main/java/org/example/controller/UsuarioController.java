package org.example.controller;

import org.example.dto.UsuarioRegistroDTO;
import org.example.dto.UsuarioRespuestaDTO;
import org.example.dto.LoginRequest;
import org.example.dto.LoginResponse; // 🔥 Importamos tu LoginResponse real
import org.example.entity.Usuario;
import org.example.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // 🔥 ENDPOINT DE LOGIN CORREGIDO CON TUS DTOs Y ROL REAL
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginDto) {
        // Ejecutamos la autenticación pasándole el email y password
        LoginResponse response = usuarioService.autenticar(loginDto.getEmail(), loginDto.getPassword());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UsuarioRespuestaDTO> crearUsuario(@RequestBody UsuarioRegistroDTO dto) {
        Usuario usuario = usuarioService.registrarUsuario(dto);
        return ResponseEntity.ok(mappearADto(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespuestaDTO> obtenerPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mappearADto(usuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRespuestaDTO>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<UsuarioRespuestaDTO> respuesta = usuarios.stream()
                .map(this::mappearADto)
                .toList();
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespuestaDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioRegistroDTO dto) {
        Usuario usuarioActualizado = usuarioService.actualizar(id, dto);
        if (usuarioActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mappearADto(usuarioActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private UsuarioRespuestaDTO mappearADto(Usuario usuario) {
        if (usuario == null) return null;
        UsuarioRespuestaDTO dto = new UsuarioRespuestaDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        return dto;
    }
}