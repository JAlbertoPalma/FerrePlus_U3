/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.CompraDTO;
import DTO.DetalleCompraDTO;
import excepciones.NegocioException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Beto_
 */
public interface ICompraBO {
    public CompraDTO agregar(CompraDTO compra) throws NegocioException;

    public CompraDTO obtenerPorId(String id) throws NegocioException;

    public CompraDTO obtenerPorFolio(String folio) throws NegocioException;

    public List<CompraDTO> obtenerTodas() throws NegocioException;

    public List<CompraDTO> obtenerPorFiltros(LocalDate fechaInicio, LocalDate fechaFin, String proveedor, String nombreProducto) throws NegocioException;

    public List<DetalleCompraDTO> obtenerDetalles(String id) throws NegocioException;
}
