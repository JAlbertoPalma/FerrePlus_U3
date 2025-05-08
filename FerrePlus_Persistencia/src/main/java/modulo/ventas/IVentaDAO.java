/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modulo.ventas;

import entidades.DetalleVenta;
import entidades.Venta;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Beto_
 */
public interface IVentaDAO {
    /**
     * Agrega una nueva venta al sistema.
     *
     * @param venta La venta a agregar, incluyendo su folio, fecha y hora, total, estado, ID de caja y detalles.
     * @return La venta agregada, incluyendo su ID generado.
     * @throws PersistenciaException Si ocurre un error durante la persistencia,
     * o si la venta no contiene detalles de productos.
     */
    public Venta agregar(Venta venta) throws PersistenciaException;
    
    /**
     * Cancela una venta existente en el sistema basada en su ID.
     * Al cancelar una venta, su estado se actualiza a inactivo.
     *
     * @param id El ID de la venta a cancelar.
     * @return La venta cancelada con su estado actualizado.
     * @throws PersistenciaException Si ocurre un error durante la persistencia
     * o si no se encuentra la venta.
     */
    public Venta cancelar(String id) throws PersistenciaException;
    
    /**
     * Obtiene una venta del sistema basada en su ID.
     *
     * @param id El ID de la venta a buscar.
     * @return La venta encontrada, o `null` si no existe.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Venta obtenerPorId(String id) throws PersistenciaException;
    
    /**
     * Obtiene una venta del sistema basada en su folio.
     *
     * @param folio El folio de la venta a buscar.
     * @return La venta encontrada, o `null` si no existe.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Venta obtenerPorFolio(String folio) throws PersistenciaException;
    
    /**
     * Obtiene todas las ventas registradas en el sistema.
     *
     * @return Una lista con todas las ventas.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public List<Venta> obtenerTodas() throws PersistenciaException;
    
    /**
     * Obtiene una lista de ventas del sistema que coinciden con los filtros proporcionados.
     * Permite filtrar por un rango de fecha y hora, proveedor (actualmente no utilizado en ventas),
     * nombre de algún producto incluido en la venta y el estado de la venta (activa/cancelada).
     *
     * @param fechaInicio     La fecha y hora de inicio del rango para filtrar (puede ser nulo).
     * @param fechaFin        La fecha y hora de fin del rango para filtrar (puede ser nulo).
     * @param proveedor       El proveedor para filtrar (actualmente no utilizado, puede ser nulo).
     * @param nombreProducto  El nombre de un producto para filtrar ventas que lo contengan (puede ser parcial o nulo).
     * @param estado          El estado de la venta para filtrar (`true` para activas, `false` para canceladas, puede ser nulo).
     * @return Una lista de ventas que cumplen con los filtros.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public List<Venta> obtenerPorFiltros(LocalDateTime fechaInicio, LocalDateTime fechaFin, String proveedor, String nombreProducto, Boolean estado) throws PersistenciaException;
    
    /**
     * Obtiene la lista de detalles de una venta específica basada en su ID.
     *
     * @param id El ID de la venta para la cual se desean obtener los detalles.
     * @return Una lista de los detalles de la venta.
     * @throws PersistenciaException Si ocurre un error durante la persistencia
     * o si no se encuentra la venta.
     */
    public List<DetalleVenta> obtenerDetalles(String id) throws PersistenciaException;
}
