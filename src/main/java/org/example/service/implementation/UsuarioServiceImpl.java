package org.example.service.implementation;

import org.example.dto.UsuarioRegistroDTO;
import org.example.dto.LoginResponse; // 🔥 Importamos tu DTO de respuesta
import org.example.entity.Usuario;
import org.example.repository.UsuarioRepository;
import org.example.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Usuario registrarUsuario(UsuarioRegistroDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido() != null ? dto.getApellido() : "");
        usuario.setEmail(dto.getEmail());
        usuario.setRol(dto.getRol() != null ? dto.getRol() : "CLIENTE");
        // 🔒 Siempre hasheamos la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario actualizar(Long id, UsuarioRegistroDTO dto) {
        Usuario usuario = obtenerPorId(id);
        if (usuario != null) {
            usuario.setNombre(dto.getNombre());
            usuario.setEmail(dto.getEmail());
            if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                // 🔒 También hasheamos al actualizar
                usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    // 🔥 AUTENTICACIÓN CORREGIDA RETORNANDO LOGINRESPONSE
    @Override
    public LoginResponse autenticar(String email, String password) {
        // 1. Buscamos el usuario por email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el email: " + email));

        // 2. Comparamos con BCrypt
        boolean passwordValida = passwordEncoder.matches(password, usuario.getPassword());
        if (!passwordValida) {
            // Fallback: comparación directa para usuarios antiguos en texto plano
            if (!password.equals(usuario.getPassword())) {
                throw new RuntimeException("Contraseña incorrecta");
            }
        }

        // 3. Generamos un token temporal/simulado (Reemplázalo por tu generador JWT si tienes uno)
        String tokenGenerado = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.tienda-gamer-token-" + usuario.getId();

        // 4. Estandarizamos el rol a "ADMIN" o "CLIENTE" para que sea compatible con el frontend
        String rolUsuario = "CLIENTE";
        if (usuario.getRol() != null) {
            String rolUpper = usuario.getRol().toUpperCase();
            if (rolUpper.contains("ADMIN")) {
                rolUsuario = "ADMIN";
            }
        }

        // 5. 🔥 Construimos y retornamos tu objeto LoginResponse con todos los datos que Angular necesita
        return new LoginResponse(
                tokenGenerado,
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                rolUsuario
        );
    }
}