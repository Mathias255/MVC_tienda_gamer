package org.example.service;

import org.example.entity.Producto;
import org.example.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    // 👇 Cambió de 'getPorCategoria' a 'obtenerPorCategoria'
    @Override
    public List<Producto> obtenerPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    // 👇 Añadimos el método 'actualizar' que faltaba
    @Override
    public Producto actualizar(Long id, Producto producto) {
        if (productoRepository.existsById(id)) {
            producto.setId(id); // Asegura que se actualice el ID correcto
            return productoRepository.save(producto);
        }
        return null;
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}