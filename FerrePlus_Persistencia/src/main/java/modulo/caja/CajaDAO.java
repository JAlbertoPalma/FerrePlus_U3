/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulo.caja;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import conexion.Conexion;
import conversores.FechaCvr;
import entidades.Caja;
import excepciones.PersistenciaException;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Implementación de la interfaz {@link ICajaDAO} para la gestión de sesiones de caja
 * utilizando la base de datos MongoDB. Esta clase sigue el patrón Singleton
 * para asegurar una única instancia de acceso a los datos de las sesiones de caja.
 * 
 * @author Beto_
 */
public class CajaDAO implements ICajaDAO{
    private final MongoCollection<Caja> collection;
    /**
     * Instancia única de la clase CompraDAO (Patrón Singleton).
     */
    private static CajaDAO instanceCajaDAO;

    /**
     * Constructor privado para evitar la instanciación directa desde fuera de la clase
     * (parte del Patrón Singleton).
     * 
     * @throws PersistenciaException Si ocurre un error al establecer la conexión con la base de datos.
     */
    private CajaDAO() throws PersistenciaException{
        try{
//            Conexion conexion = Conexion.getInstance();
//            MongoClient mongoClient = conexion.getMongoClient();
//            MongoDatabase database = conexion.getDatabase();
            MongoDatabase database = Conexion.getInstance().getDatabase();
            this.collection = database.getCollection("sesionesCaja", Caja.class);
        }catch(Exception e){
            throw new PersistenciaException("Error construyendo CajaDAO: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene la instancia única de CajaDAO (Patrón Singleton).
     * Si la instancia no existe, la crea.
     *
     * @return La instancia única de CajaDAO
     * @throws PersistenciaException si ocurre un error creando
     * la instancia de CajaDAO
     */
    public static CajaDAO getInstanceDAO() throws PersistenciaException {
        if (instanceCajaDAO == null) {
            instanceCajaDAO = new CajaDAO();
        }
        return instanceCajaDAO;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Caja abrir(Caja caja) throws PersistenciaException {
        //0. Validamos caja no nula
        if(caja == null){
            throw new PersistenciaException("No se puede abrir una caja vacía");
        }
        
        try {
            //1. Insertamos la caja directamente
            collection.insertOne(caja);
            
            //2. regresamos la caja con el id creado
            return caja;
        } catch (Exception e) {
            throw new PersistenciaException("Error al abrir la caja: " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Caja cerrar(Caja caja) throws PersistenciaException {
        //0. Validamos cajas abiertas
        if(obtenerSesionActiva() == null){
            throw new PersistenciaException("No hay cajas que cerrar");
        }
        
        //0. Validamos caja no nula
        if(caja == null){
            throw new PersistenciaException("No se puede cerrar una caja nula");
        }
        
        //0. Validamos caja no cerrada
        if(!caja.getEstadoSesion()){
            throw new PersistenciaException("No se puede cerrar una caja cerrada");
        }
        
        try {
            //0. Validamos caja existente, necesario?
            if (obtenerPorId(caja.getId().toHexString()) == null) {
                throw new PersistenciaException("No se encontró la caja en los registros");
            }

            //1. Actualizamos la caja directamente usando el objeto Caja
            collection.updateOne(Filters.eq("_id", caja.getId()), Updates.combine(
                    Updates.set("fechaHoraCierre", caja.getFechaHoraCierre()),
                    Updates.set("montoFinalEstimado", caja.getMontoFinalEstimado()),
                    Updates.set("observacionesCierre", caja.getObservacionesCierre()),
                    Updates.set("estadoSesion", false)
            ));
            
            //2. Regresamos la caja con los cambios aplicados
            return obtenerPorId(caja.getId().toHexString());
        } catch (Exception e) {
            throw new PersistenciaException("Error al cerrar la caja: " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean actualizarResumenVentas(String id, double totalVentasInc, int cantidadProductosInc, int numeroVentasInc) throws PersistenciaException{
        //0. Validamos caja existente
        if (obtenerPorId(id) == null) {
            throw new PersistenciaException("No se encontró la caja en los registros");
        }
        
        try {
            /**
             * Devolvemos el resultado de la combinación
             * Esto combina los valores del parametro con los de los atributos
             * si son positivos lo suma, si son negativos los resta
             */
            return collection.updateOne(
                    Filters.eq("_id", new ObjectId(id)),
                    Updates.combine(
                            Updates.inc("totalVentas", totalVentasInc),
                            Updates.inc("cantidadDeProductos", cantidadProductosInc),
                            Updates.inc("numeroDeVentas", numeroVentasInc)
                    )
            ).getModifiedCount() > 0; //Si los registros modificados son más de cero
        } catch (Exception e) {
            throw new PersistenciaException("Error al actualizar el resumen de ventas de la caja: " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Caja obtenerPorId(String id) throws PersistenciaException {
        //0. Vlidar id nulo
        if(id == null){
            throw new PersistenciaException("No se puede obtener una sesión de caja con id nulo");
        }
        
        try{
            //1. Creamos el objectId con el id del parametro
            Object objectId = new ObjectId(id);
            
            //2. Obtenemos el documento de la sesión de caja con el id
            Caja caja = collection.find(Filters.eq("_id", objectId)).first();
            
            //3. Retornamos la sesión de caja encontrada, nulo si no
            if(caja != null){
                return caja;
            }else{
                return null;
            }
            
        }catch(IllegalArgumentException iae){
            throw new PersistenciaException("Id inválido: " + id);
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener la sesión de caja por Id: " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Caja obtenerSesionActiva() throws PersistenciaException {
        try{
            //1. Realizamos la busqueda por la sesión activa
            Caja caja = collection.find(Filters.eq("estadoSesion", true)).first();
            
            //2. Retornamos la sesión de caja encontrada, nulo si no
            if(caja != null){
                return caja;
            }else{
                return null;
            }
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener la sesión de caja activa: " + e.getMessage());
        }
    }
}
