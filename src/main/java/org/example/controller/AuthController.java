package org.example.controller;

import org.example.dto.UsuarioRegistroDTO;
import org.example.dto.UsuarioRespuestaDTO;
import org.example.entity.Usuario;
import org.example.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioRespuestaDTO> registrar(@RequestBody UsuarioRegistroDTO dto) {
        // Guardamos usando el servicio en español
        Usuario usuarioGuardado = usuarioService.registrarUsuario(dto);

        // Convertimos la Entidad a DTO de respuesta para solucionar el tipo incompatible
        UsuarioRespuestaDTO respuesta = mappearADto(usuarioGuardado);

        return ResponseEntity.ok(respuesta);
    }

    // Método auxiliar para no repetir código de mapeo
    private UsuarioRespuestaDTO mappearADto(Usuario usuario) {
        if (usuario == null) return null;
        UsuarioRespuestaDTO dto = new UsuarioRespuestaDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        return dto;
    }
}