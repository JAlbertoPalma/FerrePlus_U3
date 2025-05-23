/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modulo.compras;

import entidades.Compra;
import entidades.DetalleCompra;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Beto_
 */
public interface ICompraDAO {
    /**
     * Agrega una nueva compra al sistema.
     *
     * @param compra La compra a agregar, incluyendo su folio, fecha, total, proveedor y detalles.
     * @return La compra agregada, incluyendo su ID generado.
     * @throws PersistenciaException Si ocurre un error durante la persistencia,
     * o si la compra no contiene detalles de productos.
     */
    public Compra agregar(Compra compra) throws PersistenciaException;
    
    /**
     * Actualiza la información de una compra existente en el sistema.
     *
     * @param compra La compra con la información actualizada.
     * @return La compra actualizada.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Compra actualizar(Compra compra) throws PersistenciaException;
    
    /**
     * Elimina una compra del sistema basada en su ID.
     *
     * @param id El ID de la compra a eliminar.
     * @return `true` si la compra fue eliminada exitosamente, `false` en caso contrario.
     * @throws PersistenciaException Si ocurre un error durante la persistencia o si no se encuentra la compra.
     */
    public boolean eliminar(String id) throws PersistenciaException;
    
    /**
     * Obtiene una compra del sistema basada en su ID.
     *
     * @param id El ID de la compra a buscar.
     * @return La compra encontrada, o `null` si no existe.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Compra obtenerPorId(String id) throws PersistenciaException;
    
    /**
     * Obtiene una compra del sistema basada en su folio.
     *
     * @param folio El folio de la compra a buscar.
     * @return La compra encontrada, o `null` si no existe.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Compra obtenerPorFolio(String folio) throws PersistenciaException;
    
    /**
     * Obtiene todas las compras registradas en el sistema.
     *
     * @return Una lista con todas las compras.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public List<Compra> obtenerTodas() throws PersistenciaException;
    
    /**
     * Obtiene una lista de compras del sistema que coinciden con los filtros proporcionados.
     * Permite filtrar por un rango de fechas, proveedor y/o el nombre de algún producto incluido en la compra.
     *
     * @param fechaInicio     La fecha de inicio del rango para filtrar (puede ser nulo).
     * @param fechaFin        La fecha de fin del rango para filtrar (puede ser nulo).
     * @param proveedor       El nombre del proveedor para filtrar (puede ser parcial o nulo).
     * @param nombreProducto  El nombre de un producto para filtrar compras que lo contengan (puede ser parcial o nulo).
     * @return Una lista de compras que cumplen con los filtros.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public List<Compra> obtenerPorFiltros(LocalDate fechaInicio, LocalDate fechaFin, String proveedor, String nombreProducto) throws PersistenciaException;
    
    /**
     * Obtiene la lista de detalles de una compra específica basada en su ID.
     *
     * @param id El ID de la compra para la cual se desean obtener los detalles.
     * @return Una lista de los detalles de la compra.
     * @throws PersistenciaException Si ocurre un error durante la persistencia
     * o si no se encuentra la compra.
     */
    public List<DetalleCompra> obtenerDetalles(String id) throws PersistenciaException;

    /**
     * Obtiene el siguiente folio consecutivo para la fecha actual del sistema,
     * basándose en los folios existentes para esa fecha. Si no hay folios para la fecha,
     * inicia la secuencia en 1.
     * El formato del folio es CP-YYYYMMDD-XXX.
     * @return El siguiente folio único.
     * @throws PersistenciaException Si ocurre un error al acceder a la base de datos.
     */
    String obtenerSiguienteFolio() throws PersistenciaException; // ¡Ya no recibe LocalDate!

}
