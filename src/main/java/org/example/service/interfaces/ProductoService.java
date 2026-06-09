package org.example.service.interfaces;

import org.example.dto.ProductoDTO;
import java.util.List;

/**
 * Interfaz de servicio para la gestión del catálogo gamer.
 * Extiende de BaseProductos para heredar operaciones fundamentales.
 *
 * @author Mathias
 * @version 1.0
 */
public interface ProductoService extends BaseProductos {

    List<ProductoDTO> obtenerTodos();

    ProductoDTO obtenerPorId(Long id);

    ProductoDTO guardar(ProductoDTO productoDto);

    // 🔥 Agregamos los métodos que te pide el ProductoController:
    ProductoDTO actualizar(Long id, ProductoDTO productoDto);

    void eliminar(Long id);

    List<ProductoDTO> obtenerPorCategoria(Long categoriaId);
}