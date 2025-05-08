/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.ProductoDTO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author joelr
 */
public interface IProductoBO {
    
    public ProductoDTO registrarProducto(ProductoDTO producto) throws NegocioException;
    public ProductoDTO actualizarProducto(ProductoDTO producto)throws NegocioException;
     public ProductoDTO obtenerProductoID(String id)throws NegocioException;
     public ProductoDTO obtenerProductoNombre(String nombre)throws NegocioException;
     public ProductoDTO obtenerProductoSKU(String sku)throws NegocioException;
     public List<ProductoDTO> obtenerProductosFiltro(String sku, String categoria, boolean estado)throws NegocioException;
     public List<ProductoDTO> obtenerProductos()throws NegocioException;
     public boolean eliminarProducto(String id) throws NegocioException, PersistenciaException;
}
