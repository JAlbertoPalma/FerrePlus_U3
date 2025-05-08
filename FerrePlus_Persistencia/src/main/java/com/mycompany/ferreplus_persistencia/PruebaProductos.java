/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ferreplus_persistencia;

import conexion.Conexion;
import entidades.Producto;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;
import modulo.inventario.IProductoDAO;
import modulo.inventario.ProductoDAO;

/**
 *
 * @author Beto_
 */
public class PruebaProductos {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        //0. Productos de prueba
         IProductoDAO productoDAO = null;
        try{
            productoDAO = ProductoDAO.getInstanceDAO();
        }catch(PersistenciaException pe){
            System.out.println("Error al instanciar DAO");
        }
        
        Producto producto1 = new Producto(
        "001", "Clavos", "General", "Kilos", 13.0, 50.0, 
        "Herreros Fregones OBSON", 50, LocalDateTime.now(), true, "Nadap");
        
        Producto producto2 = new Producto(
        "002", "Tornillos", "General", "Kilos", 10.0, 45.0, 
        "Herreros Fregones OBSON", 40, LocalDateTime.now(), true, "Nadaa");
        
        //1. Crear dos productos
        try{
            productoDAO.agregar(producto1);
            productoDAO.agregar(producto2);
            System.out.println("1. Productos agregados");
        }catch(PersistenciaException pe){
            System.out.println("1. Uhh algo salió mal: " + pe.getMessage());
        }
        
        //2. Actualizar dos productos
        try{
            producto1.setEstado(Boolean.FALSE);
            producto2.setEstado(Boolean.FALSE);
            productoDAO.actualizar(producto1);
            productoDAO.actualizar(producto2);
            System.out.println("2. Productos actualizados");
        }catch(PersistenciaException pe){
            System.out.println("2. Uhh algo salió mal: " + pe.getMessage());
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
            System.out.println("7. Eliminado: " + resultado);
        }catch(PersistenciaException pe){
            System.out.println("7. Uhh algo salió mal");
        }
    }
}
