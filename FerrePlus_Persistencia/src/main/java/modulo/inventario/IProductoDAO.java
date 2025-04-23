/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modulo.inventario;

import entidades.Producto;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * 
 * @author Beto_
 */
public interface IProductoDAO {
    /**
     * Agrega un nuevo producto al sistema.
     *
     * @param producto El producto a agregar.
     * @return El producto agregado, incluyendo su ID generado.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Producto agregar(Producto producto) throws PersistenciaException;
    
    /**
     * Actualiza la información de un producto existente en el sistema.
     *
     * @param producto El producto con la información actualizada.
     * @return El producto actualizado.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Producto actualizar(Producto producto) throws PersistenciaException;
    
    /**
     * Elimina un producto del sistema basado en su ID.
     *
     * @param id El ID del producto a eliminar.
     * @return `true` si el producto fue eliminado exitosamente, `false` en caso contrario.
     * @throws PersistenciaException Si ocurre un error durante la persistencia o si no se encuentra el producto.
     */
    public boolean eliminar(String id) throws PersistenciaException;
    
    /**
     * Obtiene un producto del sistema basado en su ID.
     *
     * @param id El ID del producto a buscar.
     * @return El producto encontrado, o `null` si no existe.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Producto obtenerPorId(String id) throws PersistenciaException;
    
    /**
     * Obtiene un producto del sistema basado en su código SKU.
     *
     * @param sku El SKU del producto a buscar.
     * @return El producto encontrado, o `null` si no existe.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Producto obtenerPorSKU(String sku) throws PersistenciaException;
    
    /**
     * Obtiene todos los productos registrados en el sistema.
     *
     * @return Una lista con todos los productos.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public List<Producto> obtenerTodos() throws PersistenciaException;
    
    /**
     * Obtiene una lista de productos del sistema que coinciden con los filtros proporcionados.
     * Los filtros de SKU y categoría realizan una búsqueda parcial e insensible a mayúsculas/minúsculas.
     *
     * @param sku       El SKU para filtrar (puede ser parcial o nulo).
     * @param categoria La categoría para filtrar (puede ser parcial o nula).
     * @param estado    El estado del producto para filtrar (puede ser nulo).
     * @return Una lista de productos que cumplen con los filtros.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public List<Producto> obtenerPorFiltros(String sku, String categoria, Boolean estado) throws PersistenciaException;
    /**
     * Obtiene un producto del sistema basado en su nombre.
     *
     * @param nombre El nombre del producto a buscar.
     * @return El producto encontrado, o `null` si no existe.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Producto obtenerPorNombre(String nombre) throws PersistenciaException;
}
