/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.DetalleVentaDTO;
import DTO.VentaDTO;
import excepciones.NegocioException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Beto_
 */
public interface IVentaBO {
    public VentaDTO agregar(VentaDTO venta) throws NegocioException;

    public VentaDTO cancelar(String id) throws NegocioException;
    
    public VentaDTO obtenerPorId(String id) throws NegocioException;

    public VentaDTO obtenerPorFolio(String folio) throws NegocioException;

    public List<VentaDTO> obtenerTodas() throws NegocioException;

    public List<VentaDTO> obtenerPorFiltros(LocalDateTime fechaInicio, LocalDateTime fechaFin, String folio, String nombreProducto, Boolean estado) throws NegocioException;

    public List<DetalleVentaDTO> obtenerDetalles(String id) throws NegocioException;
}
