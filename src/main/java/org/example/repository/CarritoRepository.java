package org.example.repository;

import org.example.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    // Busca el carrito activo de un usuario
    Optional<Carrito> findByUsuarioId(Long usuarioId);
}