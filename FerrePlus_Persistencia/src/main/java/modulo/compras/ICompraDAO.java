/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modulo.compras;

import entidades.Compra;
import entidades.Compra.DetalleCompra;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Beto_
 */
public interface ICompraDAO {
    public Compra agregar(Compra compra) throws PersistenciaException;
    
    public Compra actualizar(Compra compra) throws PersistenciaException;
    
    public boolean eliminar(String id) throws PersistenciaException;
    
    public Compra obtenerPorId(String id) throws PersistenciaException;
    
    public Compra obtenerPorFolio(String folio) throws PersistenciaException;
    
    public List<Compra> obtenerTodas() throws PersistenciaException;
    
    public List<Compra> obtenerPorFiltros(LocalDate fechaInicio, LocalDate fechaFin, String proveedor, String nombreProducto) throws PersistenciaException;
    
    public List<DetalleCompra> obtenerDetalles(String id) throws PersistenciaException;

}
