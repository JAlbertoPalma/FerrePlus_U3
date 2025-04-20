/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulo.inventario;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import conexion.Conexion;
import entidades.Producto;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author Beto_
 */
public class ProductoDAO implements IProductoDAO{
    private final MongoCollection<Document> collection;
    /**
     * Instancia única de la clase ProductoDAO (Patrón Singleton).
     */
    private static ProductoDAO instanceProductoDAO;

    /**
     * Constructor privado para evitar la instanciación directa desde fuera de la clase
     * (parte del Patrón Singleton).
     */
    private ProductoDAO() throws PersistenciaException{
        try{
            Conexion conexion = Conexion.getInstance();
            MongoClient mongoClient = conexion.getMongoClient();
            MongoDatabase database = conexion.getDatabase();
            this.collection = database.getCollection("productos");
        }catch(Exception e){
            throw new PersistenciaException("Error construyendo ProductoDAO: " + e.getMessage());
        }
    }

    /**
     * Obtiene la instancia única de ProductoDAO (Patrón Singleton).
     * Si la instancia no existe, la crea.
     *
     * @return La instancia única de ProductoDAO
     * @throws excepciones.PersistenciaException si ocurre un error creando
     * la instancia de ProductoDAO
     */
    public static ProductoDAO getInstanceDAO() throws PersistenciaException {
        if (instanceProductoDAO == null) {
            instanceProductoDAO = new ProductoDAO();
        }
        return instanceProductoDAO;
    }

    @Override
    public Producto agregar(Producto producto) throws PersistenciaException {
        //0. Validamos producto no nulo
        if(producto == null){
            throw new PersistenciaException("No se puede agregar un producto nulo");
        }
        try{
            //1. Creamos el documento del producto a agregar
            Document document = new Document()
                    .append("SKU", producto.getSKU())
                    .append("nombre", producto.getNombre())
                    .append("categoria", producto.getCategoria())
                    .append("precio_Compra_Referencial", producto.getPrecioCompraReferencial())
                    .append("precio_venta", producto.getPrecioVenta())
                    .append("proveedor", producto.getProveedor())
                    .append("stock", producto.getStock())
                    .append("estado", producto.getEstado())
                    .append("observaciones", producto.getObservaciones());
            
            //2. Insertamos el documento
            collection.insertOne(document);
            
            //3. Sacamos el id la inserción del documento y se lo ponemos al producto
            producto.setId(document.getObjectId("_id"));
            
            //4. regresamos el producto con el id creado
            return producto;
        }catch(Exception e){
            throw new PersistenciaException("Error al agregar el producto: " + e.getMessage());
        }
    }

    @Override
    public Producto actualizar(Producto producto) throws PersistenciaException {
        //0. Validamos producto no nulo
        if(producto == null){
            throw new PersistenciaException("No se puede actualizar un producto nulo");
        }
        
        try{
            //1. Le sacamos el id al producto del parámetro
            ObjectId objectId = new ObjectId(producto.getId().toHexString());
            
            //2. Creamos el documento con los atributos del parámetro
            Document document = new Document()
                    .append("SKU", producto.getSKU())
                    .append("nombre", producto.getNombre())
                    .append("categoria", producto.getCategoria())
                    .append("precio_Compra_Referencial", producto.getPrecioCompraReferencial())
                    .append("precio_venta", producto.getPrecioVenta())
                    .append("proveedor", producto.getProveedor())
                    .append("stock", producto.getStock())
                    .append("estado", producto.getEstado())
                    .append("observaciones", producto.getObservaciones());
            
            //3. Dentro de la colección reemplazamos con el id extraído el doc del producto a actualizar
            collection.replaceOne(Filters.eq("_id", objectId), document);
            
            //4. regresamos el mismo producto
            return producto;
        }catch(Exception e){
            throw new PersistenciaException("Error al actualizar el producto: " + e.getMessage());
        }
    }

    @Override
    public boolean eliminar(String id) throws PersistenciaException {
        //0. validamos id no nulo
        if(id == null || id.length() <= 0){
            throw new PersistenciaException("No se puede eliminar un producto con id nulo");
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
            
            //4. Eliminamos y regresamos resultado con filas eliminadas mayor a cero
            return collection.deleteOne(document).getDeletedCount() > 0;
            
        }catch(IllegalArgumentException iae){
            throw new PersistenciaException("Id a eliminar inválido: " + id);
        }catch(Exception e){
            throw new PersistenciaException("Error al eliminar el producto: " + e.getMessage());
        }
    }

    @Override
    public Producto obtenerPorId(String id) throws PersistenciaException {
        //0. validamos id no nulo
        if(id == null || id.length() <= 0){
            throw new PersistenciaException("No se puede buscar un producto con id nulo");
        }
        
        try{
            //1. Creamos el objectId con el id del parametro
            ObjectId objectId = new ObjectId(id);
            
            //2. Extraemos el documento con el id con la equivalencia del id, obteniendo el primer registro
            Document document = collection.find(Filters.eq("_id", objectId)).first();
            
            if(document != null){
                return toProducto(document);
            } else{
                return null;
            }
        }catch(IllegalArgumentException iae){
            throw new PersistenciaException("Id inválido: " + id);
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener el producto por Id: " + e.getMessage());
        }
    }

    @Override
    public Producto obtenerPorSKU(String sku) throws PersistenciaException {
        //0. validamos SKU no nulo
        if(sku == null || sku.length() <= 0){
            throw new PersistenciaException("No se puede buscar un producto con sku nulo");
        }
        
        try{
            //1. Extraemos el documento con la coincidencia del SKU del parámetro
            Document document = collection.find(Filters.eq("SKU", sku)).first();
            
            //2. retornamos el producto encontrado, nulo si no
            if(document != null){
                return toProducto(document);
            } else{
                return null;
            }
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener producto por SKU: " + sku);
        }
    }

    @Override
    public List<Producto> obtenerTodos() throws PersistenciaException {
        List<Producto> productos = new ArrayList<>();
        try{
            collection.find().forEach(document -> {
                productos.add(toProducto(document));
            });
            
            return productos;
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener todos los productos: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> obtenerPorFiltros(String sku, String categoria, Boolean estado) throws PersistenciaException {
        //Lista de productos filtrados
        List<Producto> productos = new ArrayList<>();
        
        //Filtros acumulados segun esten nulos o no
        List<Bson> filtros = new ArrayList<>();
        
        //1. Recopilamos filtros
        if(sku != null && !sku.isEmpty()){
            //Este patrón es para toda coincidencia con el sku utilizando
            // CASE_INSENSITIVE para no distinguir entre minusculas y mayusculas
            // ".*" para simular el LIKE de mysql, osea con que contenga el sku hay coincidencia
            Pattern patternSKU = Pattern.compile(".*" + sku + ".*", Pattern.CASE_INSENSITIVE);
            filtros.add(Filters.regex("SKU", patternSKU));
        }
        
        if(categoria != null && !categoria.isEmpty()){
            //Similar al anterior
            Pattern patternCategoria = Pattern.compile(".*" + categoria + ".*", Pattern.CASE_INSENSITIVE);
            filtros.add(Filters.regex("categoria", patternCategoria));
        }
        
        if(estado != null){
            filtros.add(Filters.eq("estado", estado));
        }
        
        //2. Juntamos los filtros
        Bson filtroTotal;
        if (!filtros.isEmpty()) {
            filtroTotal = Filters.and(filtros);
        } else {
            filtroTotal = new Document(); // Un documento vacío devuelve todos los documentos
        }
        
        //3. Aplicamos los filtros, añadiendolos a la lista de productos
        collection.find(filtroTotal).forEach(document -> {
            productos.add(toProducto(document));
        });
        
        //4. regresamos los productos filtrados
        return productos;
    }
    
    /**
     * Método auxiliar Mapper para transformar documentos JSON
     * en entidades Producto
     * @param document el documento JSON a transformar
     * @return Un producto con la información del documento JSON
     */
    private Producto toProducto(Document document){
        Producto producto = new Producto();
        producto.setId(document.getObjectId("_id"));
        producto.setSKU(document.getString("SKU"));
        producto.setNombre(document.getString("nombre"));
        producto.setCategoria(document.getString("categoria"));
        producto.setPrecioCompraReferencial(document.getDouble("precio_compra_referencial"));
        producto.setPrecioVenta(document.getDouble("precio_venta"));
        producto.setProveedor(document.getString("proveedor"));
        producto.setObservaciones(document.getString("observaciones"));
        producto.setStock(document.getInteger("stock"));
        producto.setEstado(document.getBoolean("estado"));
        return producto;
    }
    
}
