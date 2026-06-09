package org.example.service.interfaces;

import org.example.dto.UsuarioRegistroDTO;
import org.example.dto.UsuarioRespuestaDTO;
import org.example.dto.LoginResponse; // 🔥 Importante
import org.example.entity.Usuario;
import java.util.List;

public interface UsuarioService {

    Usuario registrarUsuario(UsuarioRegistroDTO dto);
    Usuario obtenerPorId(Long id);
    Usuario obtenerPorEmail(String email);
    List<Usuario> listarTodos();
    Usuario actualizar(Long id, UsuarioRegistroDTO dto);
    void eliminar(Long id);

    // 🔥 Cambiado de String a LoginResponse
    LoginResponse autenticar(String email, String password);
}