/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ferreplus_persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import conexion.Conexion;
import entidades.Producto;
import excepciones.PersistenciaException;
import java.util.List;
import modulo.inventario.IProductoDAO;
import modulo.inventario.ProductoDAO;

/**
 *
 * @author Beto_
 */
public class FerrePlus_Persistencia {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        MongoClient cliente1 = Conexion.getInstance().getMongoClient();
        MongoDatabase mongobd1 = Conexion.getInstance().getDatabase();
        
        MongoClient cliente2 = Conexion.getInstance().getMongoClient();
        MongoDatabase mongobd2 = Conexion.getInstance().getDatabase();
        
        System.out.println("Prueba de instancia de cliente única: " + (cliente1 == cliente2));
        System.out.println("Prueba de instancia de bd única: " + (mongobd1 == mongobd2));
        
        //0. Productos de prueba
         IProductoDAO productoDAO = null;
        try{
            productoDAO = ProductoDAO.getInstanceDAO();
        }catch(PersistenciaException pe){
            System.out.println("Error al instanciar DAO");
        }
        
        Producto producto1 = new Producto(
        "001", "Clavos", "General", "Kilos", 13.0, 50.0, 
        "Herreros Fregones OBSON", 50, true, "Nadap");
        
        Producto producto2 = new Producto(
        "002", "Tornillos", "General", "Kilos", 10.0, 45.0, 
        "Herreros Fregones OBSON", 40, true, "Nadaa");
        
        //1. Crear dos productos
        try{
            productoDAO.agregar(producto1);
            productoDAO.agregar(producto2);
            System.out.println("1. Productos agregados");
        }catch(PersistenciaException pe){
            System.out.println("1. Uhh algo salió mal");
        }
        
        //2. Actualizar dos productos
        try{
            producto1.setEstado(Boolean.FALSE);
            producto2.setEstado(Boolean.FALSE);
            productoDAO.actualizar(producto1);
            productoDAO.actualizar(producto2);
            System.out.println("2. Productos actualizados");
        }catch(PersistenciaException pe){
            System.out.println("2. Uhh algo salió mal");
        }
        
        //3. Buscar por id
        try{
            Producto producto = productoDAO.obtenerPorId(producto1.getId().toHexString());
            if(producto == null){
                System.out.println("3. No se encontró nada, bye");
                return;
            }
            System.out.println("3. Producto encontrado: " + producto.getNombre());
        }catch(PersistenciaException pe){
            System.out.println("3. Uhh algo salió mal");
        }
        
        //4. Buscar por sku
        try{
            Producto producto = productoDAO.obtenerPorSKU("002");
            if(producto == null){
                System.out.println("4. No se encontró nada, bye");
                return;
            }
            System.out.println("4. Producto encontrado: " + producto.getNombre());
        }catch(PersistenciaException pe){
            System.out.println("4. Uhh algo salió mal");
        }
        
        //5. Obtener todos
        try{
            List<Producto> productos = productoDAO.obtenerTodos();
            if(productos == null){
                System.out.println("5. No se encontró nada, bye");
                return;
            }
            System.out.println("5. Cantidad de productos: " + productos.size());
        }catch(PersistenciaException pe){
            System.out.println("5. Uhh algo salió mal");
        }
        
        //6. Obtener todos por filtro
        try{
            List<Producto> productos = productoDAO.obtenerPorFiltros(null, null, null);
            if(productos == null){
                System.out.println("6. No se encontró nada, bye");
                return;
            }
            System.out.println("6. Cantidad de productos: " + productos.size());
        }catch(PersistenciaException pe){
            System.out.println("6. Uhh algo salió mal");
        }
        
        //7. Eliminar un producto
        try{
            Boolean resultado = productoDAO.eliminar(producto2.getId().toHexString());
            System.out.println("Eliminado: " + resultado);
        }catch(PersistenciaException pe){
            System.out.println("7. Uhh algo salió mal");
        }
        
        //cerramos conexion
        Conexion.getInstance().cerrarConexion();
    }
}
