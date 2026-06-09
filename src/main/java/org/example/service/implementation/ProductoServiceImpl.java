package org.example.service.implementation;

import org.example.dto.ProductoDTO;
import org.example.entity.Categoria;
import org.example.entity.Producto;
import org.example.mapper.ProductoMapper;
import org.example.repository.CategoriaRepository;
import org.example.repository.ProductoRepository;
import org.example.service.interfaces.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz {@link ProductoService}.
 * Maneja la lógica de negocio del catálogo de hardware gamer, gestionando
 * las operaciones transaccionales entre la capa de presentación (DTO)
 * y la capa de persistencia (PostgreSQL).
 *
 * @author Mathias
 * @version 1.0
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    /**
     * Recupera todos los productos mapeados a DTO para consumo de la API REST.
     *
     * @return Lista de {@link ProductoDTO}
     */
    @Override
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDTO)
                .toList();
    }

    /**
     * Busca un producto por ID y lo transforma en DTO.
     *
     * @param id Identificador único del producto.
     * @return El {@link ProductoDTO} correspondiente.
     */
    @Override
    public ProductoDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en el catálogo"));
        return productoMapper.toDTO(producto);
    }

    /**
     * Guarda un nuevo producto transformando el DTO de entrada en entidad.
     *
     * @param productoDto Datos del producto.
     * @return El {@link ProductoDTO} guardado con su ID generado.
     */
    @Override
    public ProductoDTO guardar(ProductoDTO productoDto) {
        Producto producto = productoMapper.toEntity(productoDto);
        // 🔥 Cargamos la categoría completa para evitar error de referencia incompleta
        if (productoDto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(productoDto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + productoDto.getCategoriaId()));
            producto.setCategoria(categoria);
        }
        Producto guardado = productoRepository.save(producto);
        return productoMapper.toDTO(guardado);
    }

    /**
     * Actualiza un componente existente de la tienda gamer.
     * Realiza una conversión segura extrayendo el valor numérico Double del BigDecimal.
     *
     * @param id Identificador del hardware a modificar.
     * @param productoDto DTO con la nueva información.
     * @return El {@link ProductoDTO} actualizado.
     */
    @Override
    public ProductoDTO actualizar(Long id, ProductoDTO productoDto) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar, producto no encontrado"));

        productoExistente.setNombre(productoDto.getNombre());
        productoExistente.setDescripcion(productoDto.getDescripcion());
        productoExistente.setImagenUrl(productoDto.getImagenUrl());

        // 🔥 CONVERSIÓN COMPATIBLE: Extraemos el primitivo double para cumplir con la Entidad
        if (productoDto.getPrecio() != null) {
            productoExistente.setPrecio(productoDto.getPrecio().doubleValue());
        }

        productoExistente.setStock(productoDto.getStock());

        // 🔥 Actualizar categoría si viene en el DTO
        if (productoDto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(productoDto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + productoDto.getCategoriaId()));
            productoExistente.setCategoria(categoria);
        }

        Producto actualizado = productoRepository.save(productoExistente);
        return productoMapper.toDTO(actualizado);
    }

    /**
     * Elimina un producto del catálogo por su clave primaria.
     *
     * @param id Identificador único del producto.
     */
    @Override
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, el producto no existe en el sistema");
        }
        productoRepository.deleteById(id);
    }

    /**
     * Retorna todos los productos que pertenecen a una categoría específica.
     *
     * @param categoriaId ID de la categoría.
     * @return Lista de {@link ProductoDTO} filtrados por categoría.
     */
    @Override
    public List<ProductoDTO> obtenerPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId).stream()
                .map(productoMapper::toDTO)
                .toList();
    }
}