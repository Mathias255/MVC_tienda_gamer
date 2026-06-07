package org.example.controller;

import org.example.dto.UsuarioRegistroDTO;
import org.example.dto.UsuarioRespuestaDTO;
import org.example.entity.Usuario;
import org.example.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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

        // Convertimos la lista de entidades a lista de DTOs
        List<UsuarioRespuestaDTO> respuesta = usuarios.stream()
                .map(this::mappearADto)
                .toList(); // Si usas Java 16 o superior. Si usas Java 11 cambia a .collect(Collectors.toList())

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

    // Método auxiliar de mapeo
    private UsuarioRespuestaDTO mappearADto(Usuario usuario) {
        if (usuario == null) return null;
        UsuarioRespuestaDTO dto = new UsuarioRespuestaDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        return dto;
    }
}