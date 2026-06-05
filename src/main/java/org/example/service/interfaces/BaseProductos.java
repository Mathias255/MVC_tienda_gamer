package org.example.service.interfaces;

import org.example.entity.Producto;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz base que define las operaciones lógicas fundamentales
 * para la gestión del catálogo de productos en la tienda gamer.
 * * @author Mathias
 * @version 1.0
 */
public interface BaseProductos {

    /**
     * Recupera la lista completa de productos almacenados en el sistema.
     * * @return List de {@link Producto} con todos los componentes disponibles.
     */
    List<Producto> obtenerTodosLosProductos();

    /**
     * Busca un componente específico utilizando su identificador único.
     * * @param id El identificador único del producto.
     * @return Un {@link Optional} que contiene el producto si se encuentra, o vacío si no existe.
     */
    Optional<Producto> buscarPorId(Long id);
}