/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modulo.ventas;

import entidades.Venta;
import entidades.Venta.DetalleVenta;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Beto_
 */
public interface IVentaDAO {
    public Venta agregar(Venta venta) throws PersistenciaException;
    
    public Venta cancelar(String id) throws PersistenciaException;
    
    public Venta obtenerPorId(String id) throws PersistenciaException;
    
    public Venta obtenerPorFolio(String folio) throws PersistenciaException;
    
    public List<Venta> obtenerTodas() throws PersistenciaException;
    
    public List<Venta> obtenerPorFiltros(LocalDateTime fechaInicio, LocalDateTime fechaFin, String proveedor, String nombreProducto, Boolean estado) throws PersistenciaException;
    
    public List<DetalleVenta> obtenerDetalles(String id) throws PersistenciaException;
}
