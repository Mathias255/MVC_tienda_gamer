package org.example.repository;

import org.example.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    // 🚀 AGREGA ESTA NUEVA LÍNEA AQUÍ PARA SECURITY CONFIG:
    Optional<Usuario> findByEmailIgnoreCase(String email);
}