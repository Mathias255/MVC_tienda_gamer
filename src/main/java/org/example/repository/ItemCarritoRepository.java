package org.example.repository;

import org.example.entity.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
    // Obtiene todos los items que están dentro del carrito de un usuario
    List<ItemCarrito> findByCarritoId(Long carritoId);

    // Borra todos los items del carrito cuando el usuario finaliza la compra
    void deleteByCarritoId(Long carritoId);
}