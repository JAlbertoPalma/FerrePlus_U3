/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DTO.ProductoDTO;
import Interfaces.IProductoBO;
import entidades.Producto;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.ProductoMapper;
import modulo.inventario.IProductoDAO;

/**
 * Clase Business Objects (BO) para el uso de metodos de la capa negocio.
 *
 * @author Beto_
 */
public class ProductoBO implements IProductoBO {
    private IProductoDAO productoDAO;

    public ProductoBO() {
        try {
            this.productoDAO = modulo.inventario.ProductoDAO.getInstanceDAO();
        } catch (PersistenciaException ex) {
            Logger.getLogger(ProductoBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ProductoDTO registrarProducto(ProductoDTO producto) throws NegocioException {
        Producto productoARegistrar = ProductoMapper.toEntity(producto);
        try {
            Producto productoRegistrado = productoDAO.agregar(productoARegistrar);
            return ProductoMapper.toDTO(productoRegistrado);
        } catch (PersistenciaException ne) {
            throw new NegocioException("No se pudo registrar el producto");
        }
    }

    @Override
    public ProductoDTO actualizarProducto(ProductoDTO producto) throws NegocioException {
        Producto productoARegistrar = ProductoMapper.toEntity(producto);
        try {
            Producto productoRegistrado = productoDAO.actualizar(productoARegistrar);
            return ProductoMapper.toDTO(productoRegistrado);
        } catch (PersistenciaException ne) {
            throw new NegocioException("No se pudo actualizar el producto");
        }
    }
    @Override
    public boolean eliminarProducto(String id) throws NegocioException, PersistenciaException {
        try{
            this.productoDAO.eliminar(id);
        }catch(PersistenciaException pe){
            throw new NegocioException("Error al eliminar producto");
        }
        
        return false;
    }
  
    @Override
    public ProductoDTO obtenerProductoID(String id) throws NegocioException {
        try {
            Producto productoRegistrado = productoDAO.obtenerPorId(id);
            return ProductoMapper.toDTO(productoRegistrado);
        } catch (PersistenciaException ne) {
            throw new NegocioException("No se encontro el producto");
        }
    }

    @Override
    public ProductoDTO obtenerProductoNombre(String nombre) throws NegocioException {
        try {
            Producto productoRegistrado = productoDAO.obtenerPorNombre(nombre);
            return ProductoMapper.toDTO(productoRegistrado);
        } catch (PersistenciaException ne) {
            throw new NegocioException("No se encontro el producto");
        }
    }

    @Override
    public ProductoDTO obtenerProductoSKU(String sku) throws NegocioException {
        try {
            Producto productoRegistrado = productoDAO.obtenerPorSKU(sku);
            return ProductoMapper.toDTO(productoRegistrado);
        } catch (PersistenciaException ne) {
            throw new NegocioException("No se encontro el producto");
        }
    }

    @Override
    public List<ProductoDTO> obtenerProductosFiltro(String sku, String categoria, boolean estado) throws NegocioException {
        try {
            List<Producto> productosRegistrados = productoDAO.obtenerPorFiltros(sku, categoria, estado);
            return ProductoMapper.toDTOList(productosRegistrados);
        } catch (PersistenciaException ne) {
            throw new NegocioException("No se encontraron los productos");
        }
    }

    @Override
    public List<ProductoDTO> obtenerProductos() throws NegocioException {
        try {
            List<Producto> productosRegistrados = productoDAO.obtenerTodos();
            return ProductoMapper.toDTOList(productosRegistrados);
        } catch (PersistenciaException ne) {
            throw new NegocioException("No se encontraron los productos");
        }
    }

}
