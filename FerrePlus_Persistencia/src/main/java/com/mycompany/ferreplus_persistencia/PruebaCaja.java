/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.ferreplus_persistencia;

import entidades.Caja;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import modulo.caja.CajaDAO;
import modulo.caja.ICajaDAO;

/**
 *
 * @author Beto_
 */
public class PruebaCaja {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ICajaDAO cajaDAO = null;
        try {
            cajaDAO = CajaDAO.getInstanceDAO();
        } catch (PersistenciaException ex) {
            Logger.getLogger(PruebaCaja.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Caja caja = new Caja(5000.0);
        
        //1. Abrir caja
        try{
            cajaDAO.abrir(caja);
            System.out.println("1. Caja abierta");
        }catch(PersistenciaException pe){
            System.out.println("1. Uhh falló");
        }
        
        //2. Obtener caja activa
        try{
            System.out.println("2. Caja activa: " + cajaDAO.obtenerSesionActiva().getId());
        }catch(PersistenciaException pe){
            System.out.println("2. Uhh falló");
        }
        
        //3. Actualizar resumen de ventas
        try{
            cajaDAO.actualizarResumenVentas(caja.getId(), 100, 2, 1);
            caja.setTotalVentas(caja.getTotalVentas() + 100);
            caja.setCantidadDeProductos(caja.getCantidadDeProductos() + 2);
            caja.setNumeroDeVentas(caja.getNumeroDeVentas() + 1);
            System.out.println("3. Caja actualizada");
        }catch(PersistenciaException pe){
            System.out.println("3. Uhh falló");
        }
        
        //4. Obtener caja por id
        try{
            System.out.println("4. Caja por id, ventasTotal: " + cajaDAO.obtenerPorId(caja.getId().toHexString()).getTotalVentas());
        }catch(PersistenciaException pe){
            System.out.println("4. Uhh falló");
        }
        
        //5. Obtener caja por id
        try{
            caja.setMontoFinalEstimado(caja.getMontoInicial() + caja.getTotalVentas());
            caja.setFechaHoraCierre(LocalDateTime.now());
            caja.setObservacionesCierre("Prueba");
            cajaDAO.cerrar(caja);
            System.out.println("5. Caja cerrada");
        }catch(PersistenciaException pe){
            System.out.println("5. Uhh falló");
        }
        
        //6. Obtener caja activa
        try{
            if(cajaDAO.obtenerSesionActiva() != null){
                System.out.println("Caja activa (falló)");
            }else{
                System.out.println("No hay cajas activas (Pasa)");
            }
        }catch(PersistenciaException pe){
            System.out.println("6. Uhh falló");
        }
        
    }
    
}
