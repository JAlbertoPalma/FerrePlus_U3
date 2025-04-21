/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.ferreplus_persistencia;

import entidades.Venta;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modulo.ventas.IVentaDAO;
import modulo.ventas.VentaDAO;
import org.bson.types.ObjectId;

/**
 *
 * @author Beto_
 */
public class PruebaVentas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        IVentaDAO ventaDAO = null;
        
        try {
            ventaDAO = VentaDAO.getInstanceDAO();
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaCompras.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //0. ventas de prueba
        List<Venta.DetalleVenta> detalles1 = Arrays.asList(
                new Venta.DetalleVenta(new ObjectId("68061c1929db653fb7861669"), 2, 10.0, 90.0), //directo de la bd
                new Venta.DetalleVenta(new ObjectId("68061c1929db653fb7861669"), 1, 0.0, 50.0) //directo de la bd
        );
        Venta venta1 = new Venta("VOBSON1", LocalDateTime.now(), 140.0, new ObjectId("68060fd3184e5e29873b6788"), detalles1); //directo de la bd
        
        List<Venta.DetalleVenta> detalles2 = Arrays.asList(
                new Venta.DetalleVenta(new ObjectId("68061c1929db653fb7861669"), 1, 20.0, 5.0) //directo de la bd
        );
        Venta venta2 = new Venta("VOBSON2", LocalDateTime.now(), 5.0, new ObjectId("68060fd3184e5e29873b6788"), detalles2); //directo de la bd
        
        //1. Agregar dos compras
        try{
            ventaDAO.agregar(venta1);
            ventaDAO.agregar(venta2);
            System.out.println("1. ventas agregadas!");
        }catch(PersistenciaException pe){
            System.out.println("1. Error al agregar");
        }
        
        //2. buscar venta por id
        try{
            Venta venta = ventaDAO.obtenerPorId(venta1.getId().toHexString());
            System.out.println("2. Encontrada venta: " + venta.getFolio());
        }catch(PersistenciaException pe){
            System.out.println("2. Error al obtener por id");
        }        
        
        //3. buscar por folio
        try{
            Venta venta = ventaDAO.obtenerPorFolio("VOBSON2");
            System.out.println("3. Encontrada venta: " + venta.getFolio());
        }catch(PersistenciaException pe){
            System.out.println("3. Error al obtener por folio");
        } 
        
        //4. obtener todas
        try{
            System.out.println("4. Encontradas: " + ventaDAO.obtenerTodas().size());
        }catch(PersistenciaException pe){
            System.out.println("4. Error al obtener todas");
        } 
        
        //5. obtener por filtro
        try{
            System.out.println("5. Encontradas: " + ventaDAO.obtenerPorFiltros(null, null, null, null, true).size());
        }catch(PersistenciaException pe){
            System.out.println("5. Error al obtener por filtro");
        }  
        
        //6. obtener detalles
        try{
            System.out.println("6. Encontrados detalles: " + ventaDAO.obtenerDetalles(venta1.getId().toHexString()));
        }catch(PersistenciaException pe){
            System.out.println("6. Error al obtener todas");
        }
        
        //7. cancelar venta
        try{
            System.out.println("7. Estado despues de cancelar: " + ventaDAO.cancelar(venta2.getId().toHexString()).getEstado());
        }catch(PersistenciaException pe){
            System.out.println("7. Error al obtener todas");
        }
        
        //8. obtener por filtro
        try{
            System.out.println("8. Encontradas: " + ventaDAO.obtenerPorFiltros(null, null, null, null, false).size());
        }catch(PersistenciaException pe){
            System.out.println("8. Error al obtener por filtro");
        } 
    }
    
}
