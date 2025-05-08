/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulo.ventas;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import conexion.Conexion;
import conversores.FechaCvr;
import entidades.DetalleVenta;
import entidades.Producto;
import entidades.Venta;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import modulo.inventario.IProductoDAO;
import modulo.inventario.ProductoDAO;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación de la interfaz {@link IVentaDAO} para la gestión de ventas
 * utilizando la base de datos MongoDB. Esta clase sigue el patrón Singleton
 * para asegurar una única instancia de acceso a los datos de las ventas.
 * 
 * @author Beto_
 */
public class VentaDAO implements IVentaDAO{
    private final MongoCollection<Venta> collection;
    private IProductoDAO productoDAO;
    /**
     * Instancia única de la clase VentaDAO (Patrón Singleton).
     */
    private static VentaDAO instanceVentaDAO;

    /**
     * Constructor privado para evitar la instanciación directa desde fuera de la clase
     * (parte del Patrón Singleton).
     * 
     * @throws PersistenciaException Si ocurre un error al establecer la conexión con la base de datos
     * o al obtener la instancia de {@link ProductoDAO}.
     */
    private VentaDAO() throws PersistenciaException{
        try{
//            Conexion conexion = Conexion.getInstance();
//            MongoClient mongoClient = conexion.getMongoClient();
//            MongoDatabase database = conexion.getDatabase();
            MongoDatabase database = Conexion.getDatabase();
            this.collection = database.getCollection("ventas", Venta.class);
            productoDAO = ProductoDAO.getInstanceDAO();
        }catch(Exception e){
            throw new PersistenciaException("Error construyendo VentaDAO: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene la instancia única de VentaDAO (Patrón Singleton).
     * Si la instancia no existe, la crea.
     *
     * @return La instancia única de VentaDAO
     * @throws excepciones.PersistenciaException si ocurre un error creando
     * la instancia de VentaDAO
     */
    public static VentaDAO getInstanceDAO() throws PersistenciaException {
        if (instanceVentaDAO == null) {
            instanceVentaDAO = new VentaDAO();
        }
        return instanceVentaDAO;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Venta agregar(Venta venta) throws PersistenciaException {
        //0. Validamos venta no nula
        if(venta == null){
            throw new PersistenciaException("No se puede agregar una venta nula");
        }
        try{
            //1. Insertar la venta directamente
            collection.insertOne(venta);

            //2. Actualizar el stock de los productos vendidos
            if (venta.getDetalles() != null) {
                for (DetalleVenta detalle : venta.getDetalles()) {
                    Producto producto = productoDAO.obtenerPorId(detalle.getIdProducto().toHexString());
                    if (producto == null) {
                        throw new PersistenciaException("Un producto del detalle de la venta no existe");
                    }
                    //3. Actualiza el stock 
                    producto.setStock(producto.getStock() - detalle.getCantidad());
                    productoDAO.actualizar(producto);
                }
            } else {
                throw new PersistenciaException("No se puede crear una venta sin productos");
            }
            //4. Retornamos la venta, mongo le inserta el id en automático
            return venta;
        }catch(PersistenciaException pe){
            throw new PersistenciaException("Error al agregar detalles de venta: " + pe.getMessage());
        }catch(Exception e){
            throw new PersistenciaException("Error al agregar la venta: " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Venta cancelar(String id) throws PersistenciaException {
        if(id == null){
            throw new PersistenciaException("No se puede cancelar con id nulo");
        }
        try{
            Venta venta = obtenerPorId(id);
            //0. Validamos caja existente
            if(venta == null){
                throw new PersistenciaException("No se encontró la venta en los registros");
            }
            
            //0. Validamos caja existente
            if(venta.getEstado() == false){
                throw new PersistenciaException("La venta ya se encuentra cancelada");
            }
            
            //1. Combinamos el estado de la venta, cambiandolo a false, osea cancelado
            UpdateResult result = collection.updateOne(
                Filters.eq("_id", venta.getId()),
                Updates.set("estado", false)
            );
            
            //2. Si no se detectaron cambios
            if (result.getModifiedCount() == 0) {
                throw new PersistenciaException("No se pudo cancelar la venta con ID: " + id);
            }
            
            //3. Regresamos la caja con el cambio
            return obtenerPorId(id);
            
        }catch(PersistenciaException e){
            throw new PersistenciaException(e.getMessage());
        }catch(Exception e){
            throw new PersistenciaException("Error al cerrar la caja: " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Venta obtenerPorId(String id) throws PersistenciaException {
        //0. Vlidar id nulo
        if(id == null){
            throw new PersistenciaException("No se puede obtener una sesión de caja con id nulo");
        }
        
        try{
            //1. Creamos el objectId con el id del parametro
            Object objectId = new ObjectId(id);
            
            //2. Obtenemos el documento de la venta con el id
            Venta venta = collection.find(Filters.eq("_id", objectId)).first();
            
            //3. Retornamos la venta encontrada, nulo si no
            if(venta != null){
                return venta;
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
    public Venta obtenerPorFolio(String folio) throws PersistenciaException {
        //0. Validar id nulo
        if(folio == null || folio.length() <= 0){
            throw new PersistenciaException("No se puede obtener una venta con folio nulo");
        }
        try{
            
            //1. Obtenemos el documento de venta obtenido por el id
            Venta venta = collection.find(Filters.eq("folio", folio)).first();

            //2. Retornamos la venta encontrada, nulo si no
            if(venta != null){
                return venta;
            }else{
                return null;
            }
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener la venta por folio: " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Venta> obtenerTodas() throws PersistenciaException {
        List<Venta> ventas = new ArrayList<>();
        try{
            //Añade cada venta a la lista de ventas
            collection.find().forEach(venta -> {
            ventas.add(venta);
        });
        
            return ventas;
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener todas las ventas" + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Venta> obtenerPorFiltros(LocalDateTime fechaInicio, LocalDateTime fechaFin, String proveedor, String nombreProducto, Boolean estado) throws PersistenciaException {
        //Lista de ventas filtradas
        List<Venta> ventas = new ArrayList<>();
        
        /**
         * Lista de pipeline para construír las etapas de 
         * Agregación, funciona filtrando filtro tras filtro
         * hasta llegar a la consulta compleja del lookup 
         * Haciendo analogía a un sistema de tuberías (pipeline)
         * Manejando agua filtrada para filtrarla de nuevo
         * con otros filtros
         */
        List<Bson> pipeline = new ArrayList<>();
        
        /**
         * Utilizaremos Agreggates, una herramienta que nos permitirá filtrar
         * trabajando con subconjutos de cada filtro, además de crear unión con
         * otras colecciones trabajndo con $lookup
         */
        try{
            //1. Agregamos el primer filtro, pudiendo estar un valor nulo para mejores resultados
            if(fechaInicio != null && fechaFin != null){
                pipeline.add(Aggregates.match((Filters.and(Filters.gte("fecha", FechaCvr.toDate(fechaInicio)),
                                               Filters.lte("fecha", FechaCvr.toDate(fechaFin))
                ))));
            } else if (fechaInicio != null) {
                pipeline.add(Aggregates.match(Filters.gte("fecha", FechaCvr.toDate(fechaInicio))));
            } else if (fechaFin != null) {
                pipeline.add(Aggregates.match(Filters.lte("fecha", FechaCvr.toDate(fechaFin))));
            }
            
            //2. Agregamos el siguiente filtro, coincidiendo con alguna parte del proveedor con ".*" y Case insensitive "i" en MongoDB
            if(proveedor != null && !proveedor.isEmpty()){
                Pattern patternProveedor = Pattern.compile(".*" + proveedor + ".*", Pattern.CASE_INSENSITIVE);
                pipeline.add(Aggregates.match(Filters.regex("proveedor", patternProveedor)));
            }
            
            //3. Agregamos el siguiente filtro, true para confirmado, false para cancelado
            if(estado != null){
                pipeline.add(Aggregates.match(Filters.eq("estado", estado)));
            }

            /**
             * Se utiliza $lookup para crear una especie de "JOIN" y extraer el nombre del id
             * del producto que esta en el detalle dentro de venta ya que en el detalle
             * no se encuentra, así que hay que accder a él mediante una unión
             */ 
            if(nombreProducto != null && !nombreProducto.isEmpty()){
                Pattern patternProducto = Pattern.compile(".*" + nombreProducto + ".*", Pattern.CASE_INSENSITIVE);
                pipeline.add(Aggregates.lookup("productos", //from: la colección con la que uniremos
                                               "detalles.idProducto", //localField: el atributo de ventas que esta dentro del arreglo de detalles
                                               "_id", //el atributo de productos con el que vamos a unir
                                               "infoProductos")); //as: Foreignfield: El nuevo arreglo de los documentos unidos
                //Filtramos las ventas donde hay almenos una coincidencia
                //del nombre dentro de sus detalles
                pipeline.add(Aggregates.match(Filters.elemMatch("infoProductos", Filters.regex("nombre", patternProducto))));
            }
            
            /**
             * Utilizamos "var" como auxiliar, nada complejo
             * solo la utilizamos para no poner el nombre largo del cursor
             * Utilizamos un iterator ya que aggregate devuelve un Iterable -Document>
             * así que lo vamos iterando po medio del cursor de mongo, el cual
             * ponemos en un try catch with resourses para que se llame a su .close
             * al terminarse, reduciendo código y evitando que se llame a "finally"
             * independientemente si ocurre una excepción
             */
            try(var cursor = collection.aggregate(pipeline).iterator()){
                while(cursor.hasNext()){
                    //Por cada coincidencia se van añadiendo a las ventas filtradas
                    Venta venta = cursor.next();
                    ventas.add(venta);
                }
                //Regresamos las ventas filtradas
                return ventas;
            }catch(Exception e){
                throw new PersistenciaException("Error al obtener ventas por filtro (lookup): " + e.getMessage());
            }
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener ventas por filtro. " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<DetalleVenta> obtenerDetalles(String id) throws PersistenciaException {
        //0. validamos id no nulo
        if(id == null || id.length() <= 0){
            throw new PersistenciaException("No se puede obtener detalles de un id nulo");
        }
        
        try{
            //1. Creamos el objectId con el id del parametro
            ObjectId objectId = new ObjectId(id);
            
            //2. Extraemos el documento con el id con la equivalencia del id, obteniendo el primer registro
            Venta venta = collection.find(Filters.eq("_id", objectId)).first();
            
            //3. Validamos documento encontrado
            if(venta == null){
                throw new PersistenciaException("No se encontraron registros con ese id");
            }
            
            //4. Retornamos la lista de detalles
            return venta.getDetalles();
        }catch(IllegalArgumentException iae){
            throw new PersistenciaException("Id inválido: " + id);
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener los detalles de la venta: " + e.getMessage());
        }
    }
    
//    /**
//     * Método auxiliar para transformar un documento de MongoDB a una entidad Venta.
//     *
//     * @param document El documento de MongoDB a transformar.
//     * @return La entidad Venta correspondiente, incluyendo sus detalles.
//     */
//    private Venta toVenta(Document document) {
//        Venta venta = new Venta();
//        venta.setId(document.getObjectId("_id"));
//        venta.setFolio(document.getString("folio"));
//        venta.setFechaHora(FechaCvr.toLocalDateTime(document.getDate("fechaHora")));
//        venta.setTotal(document.getDouble("total"));
//        venta.setEstado(document.getBoolean("estado"));
//        venta.setIdCaja(document.getObjectId("idCaja"));
//
//        List<Document> detallesDocumentList = (List<Document>) document.get("detalles");
//        List<Venta.DetalleVenta> detallesVentaList = new ArrayList<>();
//        if (detallesDocumentList != null) {
//            for (Document detalleDoc : detallesDocumentList) {
//                Venta.DetalleVenta detalle = new Venta.DetalleVenta();
//                detalle.setIdProducto(detalleDoc.getObjectId("idProducto"));
//                detalle.setCantidad(detalleDoc.getInteger("cantidad"));
//                detalle.setDescuento(detalleDoc.getDouble("descuento"));
//                detalle.setSubtotal(detalleDoc.getDouble("subtotal"));
//                detallesVentaList.add(detalle);
//            }
//        }
//        venta.setDetalles(detallesVentaList);
//        return venta;
//    }
}
