/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulo.inventario;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import conexion.Conexion;
import conversores.FechaCvr;
import entidades.Producto;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación de la interfaz {@link IProductoDAO} para la gestión de productos
 * utilizando la base de datos MongoDB. Esta clase sigue el patrón Singleton
 * para asegurar una única instancia de acceso a los datos de los productos.
 * 
 * @author Beto_
 */
public class ProductoDAO implements IProductoDAO{
    private final MongoCollection<Producto> collection;
    /**
     * Instancia única de la clase ProductoDAO (Patrón Singleton).
     */
    private static ProductoDAO instanceProductoDAO;

     /**
     * Constructor privado para evitar la instanciación directa desde fuera de la clase
     * (parte del Patrón Singleton).
     *
     * @throws PersistenciaException Si ocurre un error al establecer la conexión con la base de datos.
     */
    public ProductoDAO() throws PersistenciaException{
        try{
            MongoDatabase database = Conexion.getInstance().getDatabase();
            this.collection = database.getCollection("productos", Producto.class);
        }catch(Exception e){
            throw new PersistenciaException("Error construyendo ProductoDAO: " + e.getMessage());
        }
    }

    /**
     * Obtiene la instancia única de ProductoDAO (Patrón Singleton).
     * Si la instancia no existe, la crea.
     *
     * @return La instancia única de ProductoDAO
     * @throws PersistenciaException si ocurre un error creando
     * la instancia de ProductoDAO
     */
    public static ProductoDAO getInstanceDAO() throws PersistenciaException {
        if (instanceProductoDAO == null) {
            instanceProductoDAO = new ProductoDAO();
        }
        return instanceProductoDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Producto agregar(Producto producto) throws PersistenciaException {
        //0. Validamos producto no nulo
        if(producto == null){
            throw new PersistenciaException("No se puede agregar un producto nulo");
        }
        try{
            //1. Insertamos el producto directamente
            collection.insertOne(producto);
            
            //2. regresamos el producto con el id creado
            return producto;
        } catch (Exception e) {
            throw new PersistenciaException("Error al agregar el producto: " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Producto actualizar(Producto producto) throws PersistenciaException {
        //0. Validamos producto no nulo
        if(producto == null){
            throw new PersistenciaException("No se puede actualizar un producto nulo");
        }
        
        try {
            Producto productoBuscado = obtenerPorId(producto.getId().toHexString());
            if(productoBuscado == null){
                throw new PersistenciaException("El producto a actualizar no existe en los registros");
            }
            
            //1. Reemplazamos el documento completo con el producto
            collection.replaceOne(Filters.eq("_id", producto.getId()), producto);
            
            //2. regresamos el mismo producto
            return producto;
        } catch (Exception e) {
            throw new PersistenciaException("Error al actualizar el producto: " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eliminar(String id) throws PersistenciaException {
        //0. validamos id no nulo
        if(id == null || id.length() <= 0){
            throw new PersistenciaException("No se puede eliminar un producto con id nulo");
        }
        
        try {
            //1. Creamos el objectId con el id del parametro
            ObjectId objectId = new ObjectId(id);

            //2. Eliminamos y regresamos resultado con filas eliminadas mayor a cero
            DeleteResult result = collection.deleteOne(Filters.eq("_id", objectId));
            return result.getDeletedCount() > 0;

        } catch (IllegalArgumentException iae) {
            throw new PersistenciaException("Id a eliminar inválido: " + id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al eliminar el producto: " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
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
            Producto producto = collection.find(Filters.eq("_id", objectId)).first();
            
            if(producto != null){
                return producto;
            } else{
                return null;
            }
        }catch(IllegalArgumentException iae){
            throw new PersistenciaException("Id inválido: " + id);
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener el producto por Id: " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Producto obtenerPorSKU(String sku) throws PersistenciaException {
        //0. validamos SKU no nulo
        if(sku == null || sku.length() <= 0){
            throw new PersistenciaException("No se puede buscar un producto con sku nulo");
        }
        
        try{
            //1. Extraemos el documento con la coincidencia del SKU del parámetro
            Producto producto = collection.find(Filters.eq("sku", sku)).first();
            
            //2. retornamos el producto encontrado, nulo si no
            if(producto != null){
                return producto;
            } else{
                return null;
            }
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener producto por SKU: " + sku);
        }
    }
    @Override
    public Producto obtenerPorNombre(String nombre) throws PersistenciaException {
        //0. validamos SKU no nulo
        if(nombre == null){
            throw new PersistenciaException("No se puede buscar un producto con sku nulo");
        }
        
        try{
            //1. Extraemos el documento con la coincidencia del SKU del parámetro
            Producto producto = collection.find(Filters.eq("nombre", nombre)).first();
            
            //2. retornamos el producto encontrado, nulo si no
            if(producto != null){
                return producto;
            } else{
                return null;
            }
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener producto por nombre: " + nombre);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Producto> obtenerTodos() throws PersistenciaException {
        List<Producto> productos = new ArrayList<>();
        try{
            collection.find().forEach(producto -> {
                productos.add(producto);
            });
            
            return productos;
        }catch(Exception e){
            throw new PersistenciaException("Error al obtener todos los productos: " + e.getMessage());
        }
    }
    
    /**
     * @param sku the value of sku
     * @param categoria the value of categoria
     * @param estado the value of estado
     * @return 
     * @throws PersistenciaException
     */
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
            filtros.add(Filters.regex("sku", patternSKU));
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
        collection.find(filtroTotal).forEach(producto -> {
            productos.add(producto);
        });
        
        //4. regresamos los productos filtrados
        return productos;
    }
}
