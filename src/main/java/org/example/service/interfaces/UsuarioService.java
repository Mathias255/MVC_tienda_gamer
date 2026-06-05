package org.example.service.interfaces; // 🚀 ¡IMPORTANTE!: Agregar el .interfaces aquí

import org.example.dto.LoginRequest;
import org.example.dto.UsuarioRegistroDTO;
import org.example.dto.UsuarioRespuestaDTO;

public interface UsuarioService {
    UsuarioRespuestaDTO registrar(UsuarioRegistroDTO registroDTO);
    UsuarioRespuestaDTO login(LoginRequest loginRequest);
}