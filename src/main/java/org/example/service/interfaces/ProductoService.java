package org.example.service.interfaces;

import org.example.entity.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> obtenerTodos();
    Producto obtenerPorId(Long id);
    Producto guardar(Producto producto);

    // 👇 Nombres corregidos para que coincidan con el controlador:
    List<Producto> obtenerPorCategoria(Long categoriaId);
    Producto actualizar(Long id, Producto producto);

    void eliminar(Long id);
}