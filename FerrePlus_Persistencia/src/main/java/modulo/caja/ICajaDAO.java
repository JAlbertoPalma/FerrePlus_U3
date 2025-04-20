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
    public Caja abrir(Caja caja) throws PersistenciaException;
    
    public Caja cerrar(Caja caja) throws PersistenciaException;
    
    public boolean actualizarResumenVentas(ObjectId id, double totalVentasInc, int cantidadProductosInc, int numeroVentasInc) throws PersistenciaException;
    
    public Caja obtenerPorId(String id) throws PersistenciaException;
    
    public Caja obtenerSesionActiva() throws PersistenciaException;
    
}
