/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulo.compras;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import conexion.Conexion;
import conversores.FechaCvr;
import entidades.Compra;
import entidades.Producto;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import modulo.inventario.IProductoDAO;
import modulo.inventario.ProductoDAO;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author Beto_
 */
public class CompraDAO implements ICompraDAO{
    private final MongoCollection<Document> collection;
    private IProductoDAO productoDAO;
    /**
     * Instancia única de la clase CompraDAO (Patrón Singleton).
     */
    private static CompraDAO instanceCompraDAO;

    /**
     * Constructor privado para evitar la instanciación directa desde fuera de la clase
     * (parte del Patrón Singleton).
     */
    private CompraDAO() throws PersistenciaException{
        try{
            Conexion conexion = Conexion.getInstance();
            MongoClient mongoClient = conexion.getMongoClient();
            MongoDatabase database = conexion.getDatabase();
            this.collection = database.getCollection("compras");
            productoDAO = ProductoDAO.getInstanceDAO();
        }catch(Exception e){
            throw new PersistenciaException("Error construyendo ProductoDAO: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene la instancia única de CompraDAO (Patrón Singleton).
     * Si la instancia no existe, la crea.
     *
     * @return La instancia única de CompraDAO
     * @throws excepciones.PersistenciaException si ocurre un error creando
     * la instancia de ProductoDAO
     */
    public static CompraDAO getInstanceDAO() throws PersistenciaException {
        if (instanceCompraDAO == null) {
            instanceCompraDAO = new CompraDAO();
        }
        return instanceCompraDAO;
    }
    
    @Override
    public Compra agregar(Compra compra) throws PersistenciaException {
        //0. Validamos compra no nula
        if(compra == null){
            throw new PersistenciaException("No se puede agregar una compra nula");
        }
        try{
            //1. Creamos le documento de la compra a agregar
            Document document = new Document()
                    .append("folio", compra.getFolio())
                    .append("fecha", FechaCvr.toDate(compra.getFecha()))
                    .append("total", compra.getTotal())
                    .append("proveedor", compra.getProveedor());
            
            //2 Creamos una lista de documentos donde guardaremos los detalles de la compra
            List<Document> listaDetallesDocuments = new ArrayList<>();
            if(compra.getDetalles() != null){ 
                for (Compra.DetalleCompra detalle : compra.getDetalles()) {
                    
                    //2.1 Sacamos el producto del detalle para trabajarlo
                    Producto producto = productoDAO.obtenerPorId(detalle.getIdProducto().toHexString());
                    
                    //2.2 Validamos el id del producto
                    if(producto == null){
                        throw new PersistenciaException("Un producto del detalle de la compra no existe");
                    }
                    
                    //2.3 Actualizamos el precio referencial con el precio de compra
                    if(!detalle.getPrecioDeCompra().equals(producto.getPrecioCompraReferencial())){
                        producto.setPrecioCompraReferencial(detalle.getPrecioDeCompra());
                        productoDAO.actualizar(producto);
                    }
                    
                    //2.4 Actualizamos el stock, aumentando por la cantidad comprada
                    producto.setStock(producto.getStock() + detalle.getCantidad());
                    productoDAO.actualizar(producto);
                    
                    //2.5 Creamos un documento con la información del detalle y la añadimos a la lista
                    Document detalleDocument = new Document()
                            .append("idProducto", detalle.getIdProducto())
                            .append("cantidad", detalle.getCantidad())
                            .append("precioDeCompra", detalle.getPrecioDeCompra())
                            .append("subtotal", detalle.getSubtotal());
                    listaDetallesDocuments.add(detalleDocument);
                }
            }
            
            //3. Añadimos la lista de documentos de detalles a la compra
            document.append("detalles", listaDetallesDocuments);
            
            //4. Insertamos el documento
            collection.insertOne(document);
            
            //5. Extraemos le id de la inserción del documento y se lo ponemos a la compra
            compra.setId(document.getObjectId("_id"));
            
            //6. Regresamos la compra ya con el id
            return compra;
        }catch(PersistenciaException pe){
            throw new PersistenciaException("Error al agregar detalles de compra: " + pe.getMessage());
        }catch(Exception e){
            throw new PersistenciaException("Error al agregar la compra: " + e.getMessage());
        }
    }

    @Override
    public Compra actualizar(Compra compra) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(String id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Compra obtenerPorId(String id) throws PersistenciaException {
        //0. Validar id nulo
        if(id == null || id.length() <= 0){
            throw new PersistenciaException("No se puede obtener una compra con id nulo");
        }
        try{
            //1. Creamos el objectId con el parametro
            ObjectId objectId = new ObjectId(id);

            //2. Obtenemos el documento de compra obtenido por el id
            Document document = collection.find(Filters.eq("_id", objectId)).first();

            //3. Retornamos la compra encontrada, nulo si no
            if(document != null){
                return toCompra(document);
            }else{
                return null;
            }
        }catch(IllegalArgumentException iae){
            throw new PersistenciaException("Id inválido: " + id);
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener la compra por Id: " + e.getMessage());
        }
    }

    @Override
    public Compra obtenerPorFolio(String folio) throws PersistenciaException {
        //0. Validar id nulo
        if(folio == null || folio.length() <= 0){
            throw new PersistenciaException("No se puede obtener una compra con folio nulo");
        }
        try{
            
            //1. Obtenemos el documento de compra obtenido por el id
            Document document = collection.find(Filters.eq("folio", folio)).first();

            //2. Retornamos la compra encontrada, nulo si no
            if(document != null){
                return toCompra(document);
            }else{
                return null;
            }
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener la compra por folio: " + e.getMessage());
        }
    }

    @Override
    public List<Compra> obtenerTodas() throws PersistenciaException {
        List<Compra> compras = new ArrayList<>();
        try{
            collection.find().forEach(document -> {
            compras.add(toCompra(document));
        });
        
            return compras;
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener todas las compras" + e.getMessage());
        }
    }

    @Override
    public List<Compra> obtenerPorFiltros(LocalDate fechaInicio, LocalDate fechaFin, String proveedor, String nombreProducto) throws PersistenciaException {
        //Lista de compras filtradas
        List<Compra> compras = new ArrayList<>();
        
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

            /**
             * Se utiliza $lookup para crear una especie de "JOIN" y extraer el nombre del id
             * del producto que esta en el detalle dentro de compra ya que en el detalle
             * no se encuentra, así que hay que accder a él mediante una unión
             */ 
            if(nombreProducto != null && !nombreProducto.isEmpty()){
                Pattern patternProducto = Pattern.compile(".*" + nombreProducto + ".*", Pattern.CASE_INSENSITIVE);
                pipeline.add(Aggregates.lookup("productos", //from: la colección con la que uniremos
                                               "detalles.idProducto", //localField: el atributo de compras que esta dentro del arreglo de detalles
                                               "_id", //el atributo de productos con el que vamos a unir
                                               "infoProductos")); //as: Foreignfield: El nuevo arreglo de los documentos unidos
                //Filtramos las compras donde hay almenos una coincidencia
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
                    //Por cada coincidencia se van añadiendo a las compras filtradas
                    Document doc = cursor.next();
                    compras.add(toCompra(doc));
                }
                //Regresamos las compras filtradas
                return compras;
            }catch(Exception e){
                throw new PersistenciaException("Error al obtener compras por filtro (lookup): " + e.getMessage());
            }
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener compras por filtro. " + e.getMessage());
        }
    }

    @Override
    public List<Compra.DetalleCompra> obtenerDetalles(String id) throws PersistenciaException {
        //0. validamos id no nulo
        if(id == null || id.length() <= 0){
            throw new PersistenciaException("No se puede obtener detalles de un id nulo");
        }
        
        try{
            //1. Creamos el objectId con el id del parametro
            ObjectId objectId = new ObjectId(id);
            
            //2. Extraemos el documento con el id con la equivalencia del id, obteniendo el primer registro
            Document document = collection.find(Filters.eq("_id", objectId)).first();
            
            //3. Validamos documento encontrado
            if(document == null){
                throw new PersistenciaException("No se encontraron registros con ese id");
            }
            
            //4. Retornamos la lista de detalles
            return toCompra(document).getDetalles();
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener los detalles de la compra con id: " + id + ". " + e.getMessage());
        }
    }
    
    private Compra toCompra(Document document){
        Compra compra = new Compra();
        compra.setId(document.getObjectId("_id"));
        compra.setFolio(document.getString("folio"));
        compra.setFecha(FechaCvr.toLocalDate(document.getDate("fecha")));
        compra.setTotal(document.getDouble("total"));
        compra.setProveedor(document.getString("proveedor"));

        List<Document> detallesDocumentList = (List<Document>) document.get("detalles");
        List<Compra.DetalleCompra> detallesCompraList = new ArrayList<>();
        if (detallesDocumentList != null) {
            for (Document detalleDoc : detallesDocumentList) {
                Compra.DetalleCompra detalle = new Compra.DetalleCompra();
                detalle.setIdProducto(detalleDoc.getObjectId("idProducto"));
                detalle.setCantidad(detalleDoc.getInteger("cantidad"));
                detalle.setPrecioDeCompra(detalleDoc.getDouble("precioDeCompra"));
                detalle.setSubtotal(detalleDoc.getDouble("subtotal"));
                detallesCompraList.add(detalle);
            }
        }
        compra.setDetalles(detallesCompraList);
        return compra;
    }
    
}
