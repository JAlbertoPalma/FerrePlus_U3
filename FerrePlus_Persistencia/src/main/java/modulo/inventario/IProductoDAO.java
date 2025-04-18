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
    public Producto agregar(Producto producto) throws PersistenciaException;
    
    public Producto actualizar(Producto producto) throws PersistenciaException;
    
    public boolean eliminar(String id) throws PersistenciaException;
    
    public Producto obtenerPorId(String id) throws PersistenciaException;
    
    public Producto obtenerPorSKU(String sku) throws PersistenciaException;
    
    public List<Producto> obtenerTodos() throws PersistenciaException;
    
    public List<Producto> obtenerPorFiltros(String sku, String categoria, Boolean estado) throws PersistenciaException;
}
