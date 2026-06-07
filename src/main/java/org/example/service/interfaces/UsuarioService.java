package org.example.service.interfaces;

import org.example.dto.UsuarioRegistroDTO;
import org.example.entity.Usuario; // Asegúrate de que apunte a tu paquete de entidades
import java.util.List;

public interface UsuarioService {

    Usuario registrarUsuario(UsuarioRegistroDTO dto);

    Usuario obtenerPorId(Long id);

    Usuario obtenerPorEmail(String email);

    List<Usuario> listarTodos();

    Usuario actualizar(Long id, UsuarioRegistroDTO dto);

    void eliminar(Long id);
}