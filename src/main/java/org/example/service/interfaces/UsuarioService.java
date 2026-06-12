package org.example.service.interfaces;

import org.example.entity.Usuario;
import org.example.dto.UsuarioRegistroDTO;
import org.example.dto.LoginResponse;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listarTodos();
    Optional<Usuario> obtenerPorId(Long id);
    Usuario registrarUsuario(UsuarioRegistroDTO usuarioDTO);
    Usuario actualizar(Long id, UsuarioRegistroDTO usuarioDTO);
    void eliminar(Long id);
    LoginResponse autenticar(String email, String password);
}