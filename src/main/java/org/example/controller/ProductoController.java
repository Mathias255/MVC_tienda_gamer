package org.example.controller;

import org.example.dto.ProductoDTO;
import org.example.entity.Producto;
import org.example.mapper.ProductoMapper;
import org.example.repository.ProductoRepository;
import org.example.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoMapper productoMapper;

    // Obtener todos los productos convertidos a DTO limpios (ej: para la vitrina gamer)
    @GetMapping
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(p -> productoMapper.toDTO(p))
                .collect(Collectors.toList());
    }

    // Obtener un producto específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(p -> ResponseEntity.ok(productoMapper.toDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear o agregar un nuevo componente gamer vinculando su categoría
    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO productoDTO) {
        Producto producto = productoMapper.toEntity(productoDTO);

        // Si el DTO incluye un ID de categoría válido, lo buscamos y se lo asociamos a la entidad
        if (productoDTO.getCategoriaId() != null) {
            categoriaRepository.findById(productoDTO.getCategoriaId())
                    .ifPresent(categoria -> producto.setCategoria(categoria));
        }

        Producto nuevoProducto = productoRepository.save(producto);
        ProductoDTO respuesta = productoMapper.toDTO(nuevoProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // Eliminar un producto de la tienda
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}