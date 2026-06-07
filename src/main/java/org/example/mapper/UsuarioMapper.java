package org.example.mapper;

import org.example.dto.UsuarioRegistroDTO;
import org.example.dto.UsuarioRespuestaDTO;
import org.example.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRegistroDTO dto) {
        if (dto == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido()); // Mantenemos tu corrección del apellido
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());

        if (dto.getRol() != null) {
            if (dto.getRol().equalsIgnoreCase("CLIENTE")) {
                usuario.setRol("Cliente");
            } else if (dto.getRol().equalsIgnoreCase("ADMINISTRADOR")) {
                usuario.setRol("Administrador");
            } else {
                usuario.setRol(dto.getRol());
            }
        } else {
            usuario.setRol("Cliente");
        }

        return usuario;
    }

    public UsuarioRespuestaDTO toRespuestaDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioRespuestaDTO dto = new UsuarioRespuestaDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        return dto;
    }
}