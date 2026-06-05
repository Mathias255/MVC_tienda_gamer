package org.example.service.implementation;

import org.example.dto.LoginRequest;
import org.example.dto.UsuarioRegistroDTO;
import org.example.dto.UsuarioRespuestaDTO;
import org.example.entity.Usuario;
import org.example.mapper.UsuarioMapper;
import org.example.repository.UsuarioRepository;
import org.example.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UsuarioRespuestaDTO registrar(UsuarioRegistroDTO registroDTO) {
        // 1. Convertimos el DTO que viene de Angular a la entidad Usuario
        Usuario usuario = usuarioMapper.toEntity(registroDTO);

        // 🚀 LO NUEVO: Validar el apellido para evitar el error 500 de PostgreSQL
        // Si el apellido viene nulo o vacío de Angular, le ponemos un texto vacío ("")
        // para que no rompa la restricción NOT NULL de tu base de datos.
        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            usuario.setApellido("");
        }

        // 2. Encriptamos la contraseña con BCrypt
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        // 3. Guardamos en la base de datos de forma segura
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 4. Retornamos la respuesta mapeada a DTO
        return usuarioMapper.toRespuestaDTO(usuarioGuardado);
    }

    @Override
    public UsuarioRespuestaDTO login(LoginRequest loginRequest) {
        // Buscamos al usuario ignorando mayúsculas/minúsculas
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailIgnoreCase(loginRequest.getEmail());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Comparamos la contraseña encriptada
            if (passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                return usuarioMapper.toRespuestaDTO(usuario);
            }
        }
        return null; // Retorna null si las credenciales no coinciden
    }
}