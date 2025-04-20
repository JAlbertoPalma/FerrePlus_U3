/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.ferreplus_persistencia;

import entidades.Compra;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modulo.compras.CompraDAO;
import modulo.compras.ICompraDAO;
import org.bson.types.ObjectId;

/**
 *
 * @author Beto_
 */
public class PruebaCompras {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ICompraDAO compraDAO = null;
        
        try {
            compraDAO = CompraDAO.getInstanceDAO();
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaCompras.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //0. Compras de prueba
        List<Compra.DetalleCompra> detalles1 = Arrays.asList(
                new Compra.DetalleCompra(new ObjectId("6803305d2f6b9d23e6e6d754"), 2, 15.0, 30.0),
                new Compra.DetalleCompra(new ObjectId("6803305d2f6b9d23e6e6d754"), 1, 15.0, 15.0)
        );
        Compra compra1 = new Compra("OBSON1", LocalDate.now(), 45.0, "Distribuidora del Norte", detalles1);
        
        List<Compra.DetalleCompra> detalles2 = Arrays.asList(
                new Compra.DetalleCompra(new ObjectId("6803305d2f6b9d23e6e6d754"), 5, 15.0, 75.0)
        );
        Compra compra2 = new Compra("OBSON2", LocalDate.now(), 75.0, "Distribuidora del Norte", detalles2);
        
        //1. Agregar dos compras
//        try{
//            compraDAO.agregar(compra1);
//            compraDAO.agregar(compra2);
//            System.out.println("1. Compras agregadas!");
//        }catch(PersistenciaException pe){
//            System.out.println("1. Error al agregar");
//        }
        
        //2. buscar compra por id
        try{
            Compra compra = compraDAO.obtenerPorId("680442066ac1cb0f1d865599");
            System.out.println("2. Encontrada compra: " + compra.getFolio());
        }catch(PersistenciaException pe){
            System.out.println("2. Error al obtener por id");
        }        
        
        //3. buscar por folio
        try{
            Compra compra = compraDAO.obtenerPorFolio("OBSON2");
            System.out.println("3. Encontrada compra: " + compra.getFolio());
        }catch(PersistenciaException pe){
            System.out.println("3. Error al obtener por folio");
        } 
        
        //4. obtener todas
        try{
            System.out.println("4. Encontradas: " + compraDAO.obtenerTodas().size());
        }catch(PersistenciaException pe){
            System.out.println("4. Error al obtener todas");
        } 
        
        //5. obtener por filtro
        try{
            System.out.println("5. Encontradas: " + compraDAO.obtenerPorFiltros(null, null, null, null).size());
        }catch(PersistenciaException pe){
            System.out.println("5. Error al obtener por filtro");
        }  
        
        //6. obtener detalles
        try{
            System.out.println("6. Encontrada compra: " + compraDAO.obtenerDetalles("680442066ac1cb0f1d865599"));
        }catch(PersistenciaException pe){
            System.out.println("6. Error al obtener todas");
        } 
        
    }
}
