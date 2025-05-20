/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.CajaDTO;
import excepciones.NegocioException;

/**
 *
 * @author Beto_
 */
public interface ICajaBO {
    public CajaDTO abrir(CajaDTO caja) throws NegocioException;
    
    public CajaDTO cerrar(CajaDTO caja) throws NegocioException;
    
    public boolean actualizarResumenVentas(String id, double totalVentasInc, int cantidadProductosInc, int numeroVentasInc) throws NegocioException;
    
    public CajaDTO obtenerPorId(String id) throws NegocioException;
    
    public CajaDTO obtenerSesionActiva() throws NegocioException;
}
