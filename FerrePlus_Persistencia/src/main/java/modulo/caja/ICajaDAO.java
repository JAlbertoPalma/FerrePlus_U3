/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modulo.caja;

import entidades.Caja;
import excepciones.PersistenciaException;
import org.bson.types.ObjectId;

/**
 *
 * @author Beto_
 */
public interface ICajaDAO {
    /**
     * Abre una nueva sesión de caja en el sistema.
     *
     * @param caja La información de la caja a abrir, incluyendo el monto inicial.
     * @return La instancia de la caja abierta, con su ID generado.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Caja abrir(Caja caja) throws PersistenciaException;
    
    /**
     * Cierra una sesión de caja activa en el sistema.
     *
     * @param caja La información de la caja a cerrar, incluyendo la fecha y hora de cierre,
     * el monto final estimado y las observaciones del cierre.
     * @return La instancia de la caja cerrada con la información actualizada.
     * @throws PersistenciaException Si ocurre un error durante la persistencia
     * o si no hay una sesión de caja activa para cerrar.
     */
    public Caja cerrar(Caja caja) throws PersistenciaException;
    
    /**
     * Actualiza el resumen de ventas de una sesión de caja específica.
     * Permite incrementar el total de ventas, la cantidad de productos vendidos
     * y el número de ventas realizadas.
     *
     * @param id                 El ID de la sesión de caja a actualizar.
     * @param totalVentasInc     El incremento en el total de ventas.
     * @param cantidadProductosInc El incremento en la cantidad de productos vendidos.
     * @param numeroVentasInc    El incremento en el número de ventas.
     * @return `true` si la actualización fue exitosa, `false` en caso contrario.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public boolean actualizarResumenVentas(String id, double totalVentasInc, int cantidadProductosInc, int numeroVentasInc) throws PersistenciaException;
    
    /**
     * Obtiene una sesión de caja del sistema basada en su ID.
     *
     * @param id El ID de la sesión de caja a buscar.
     * @return La sesión de caja encontrada, o `null` si no existe.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Caja obtenerPorId(String id) throws PersistenciaException;
    
    /**
     * Obtiene la sesión de caja que actualmente está activa en el sistema.
     *
     * @return La sesión de caja activa, o `null` si no hay ninguna.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public Caja obtenerSesionActiva() throws PersistenciaException;
}
