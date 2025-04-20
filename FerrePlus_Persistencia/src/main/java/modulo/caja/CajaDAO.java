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
 *
 * @author Beto_
 */
public class CajaDAO implements ICajaDAO{
    private final MongoCollection<Document> collection;
    /**
     * Instancia única de la clase CompraDAO (Patrón Singleton).
     */
    private static CajaDAO instanceCajaDAO;

    /**
     * Constructor privado para evitar la instanciación directa desde fuera de la clase
     * (parte del Patrón Singleton).
     */
    private CajaDAO() throws PersistenciaException{
        try{
            Conexion conexion = Conexion.getInstance();
            MongoClient mongoClient = conexion.getMongoClient();
            MongoDatabase database = conexion.getDatabase();
            this.collection = database.getCollection("sesionesCaja");
        }catch(Exception e){
            throw new PersistenciaException("Error construyendo CajaDAO: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene la instancia única de CajaDAO (Patrón Singleton).
     * Si la instancia no existe, la crea.
     *
     * @return La instancia única de CajaDAO
     * @throws excepciones.PersistenciaException si ocurre un error creando
     * la instancia de CajaDAO
     */
    public static CajaDAO getInstanceDAO() throws PersistenciaException {
        if (instanceCajaDAO == null) {
            instanceCajaDAO = new CajaDAO();
        }
        return instanceCajaDAO;
    }

    @Override
    public Caja abrir(Caja caja) throws PersistenciaException {
        //0. Validamos caja no nula
        if(caja == null){
            throw new PersistenciaException("No se puede abrir una caja vacía");
        }
        try{
            //1. Creamos el documento de la caja para guardar en la colección
            Document document = new Document()
                    .append("fechaHoraApertura", FechaCvr.toDate(caja.getFechaHoraApertura()))
                    .append("montoInicial", caja.getMontoInicial())
                    .append("estadoSesion", caja.getEstadoSesion())
                    .append("totalVentas", caja.getTotalVentas())
                    .append("cantidadDeProductos", caja.getCantidadDeProductos())
                    .append("numeroDeVentas", caja.getNumeroDeVentas());
            
            //2. Insertamos el documento
            collection.insertOne(document);
            
            //3. Extraemos el id para inscrustarlo en la caja del parametro
            ObjectId id = document.getObjectId("_id");
            caja.setId(id);
            
            //4. regresamos la caja con el id
            return caja;
        }catch(Exception e){
            throw new PersistenciaException("Error al abrir la caja: " + e.getMessage());
        }
    }

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
        
        try{
            //0. Validamos caja existente
            if(obtenerPorId(caja.getId().toHexString()) == null){
                throw new PersistenciaException("No se encontró la caja en los registros");
            }
            
            //1. Combinamos los valores de cierre
            // Evolucionando con su cierre, estando completa y sellada
            collection.updateOne(
                Filters.eq("_id", caja.getId()),
                Updates.combine(
                        Updates.set("fechaHoraCierre", FechaCvr.toDate(caja.getFechaHoraCierre())),
                        Updates.set("montoFinalEstimado", caja.getMontoFinalEstimado()),
                        Updates.set("observacionesCierre", caja.getObservacionesCierre()),
                        Updates.set("estadoSesion", false)
                )
            );
            
            //2. Regresamos la caja con los cambios aplicados
        return obtenerPorId(caja.getId().toHexString());
        }catch(Exception e){
            throw new PersistenciaException("Error al cerrar la caja: " + e.getMessage());
        }
    }
    
    @Override
    public boolean actualizarResumenVentas(ObjectId id, double totalVentasInc, int cantidadProductosInc, int numeroVentasInc) throws PersistenciaException{
        try {
            /**
             * Devolvemos el resultado de la combinación
             * Esto combina los valores del parametro con los de los atributos
             * si son positivos lo suma, si son negativos los resta
             */
            return collection.updateOne(
                    Filters.eq("_id", id),
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
            Document document = collection.find(Filters.eq("_id", objectId)).first();
            
            //3. Retornamos la sesión de caja encontrada, nulo si no
            if(document != null){
                return toCaja(document);
            }else{
                return null;
            }
            
        }catch(IllegalArgumentException iae){
            throw new PersistenciaException("Id inválido: " + id);
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener la sesión de caja por Id: " + e.getMessage());
        }
    }

    @Override
    public Caja obtenerSesionActiva() throws PersistenciaException {
        try{
            //1. Realizamos la busqueda por la sesión activa
            Document document = collection.find(Filters.eq("estadoSesion", true)).first();
            
            //2. Retornamos la sesión de caja encontrada, nulo si no
            if(document != null){
                return toCaja(document);
            }else{
                return null;
            }
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener la sesión de caja activa: " + e.getMessage());
        }
    }
    
    private Caja toCaja(Document document) throws PersistenciaException {
        if (document == null) {
            return null;
        }
        Caja caja = new Caja();
        caja.setId(document.getObjectId("_id"));
        caja.setFechaHoraCierre(FechaCvr.toLocalDateTime(document.getDate("fechaHoraApertura")));
        caja.setMontoInicial(document.getDouble("montoInicial"));
        caja.setEstadoSesion(document.getBoolean("estadoSesion"));
        caja.setTotalVentas(document.getDouble("totalVentas"));
        caja.setCantidadDeProductos(document.getInteger("cantidadDeProductos"));
        caja.setNumeroDeVentas(document.getInteger("numeroDeVentas"));
        caja.setFechaHoraCierre(FechaCvr.toLocalDateTime(document.getDate("fechaHoraCierre")));
        caja.setMontoFinalEstimado(document.getDouble("montoFinalEstimado"));
        caja.setObservacionesCierre(document.getString("observacionesCierre"));
        return caja;
    }
}
