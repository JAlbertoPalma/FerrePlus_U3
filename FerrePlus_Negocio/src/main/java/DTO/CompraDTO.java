/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) que contiene la información completa de una compra,
 * incluyendo su identificador único y la lista de detalles de los productos comprados.
 * Se utiliza para transferir datos de compras existentes entre las capas de la aplicación.
 * 
 * @author Beto_
 */
public class CompraDTO {
    private String id;
    private String folio;
    private LocalDate fecha;
    private Double total;
    private String proveedor;
    private List<DetalleCompraDTO> detalles;

    public CompraDTO() {
        detalles = new ArrayList<>();
    }

    public CompraDTO(String id, String folio, LocalDate fecha, Double total, String proveedor, List<DetalleCompraDTO> detalles) {
        this.id = id;
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
        this.proveedor = proveedor;
        this.detalles = detalles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public List<DetalleCompraDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCompraDTO> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "CompraDTO{" + "id=" + id + ", folio=" + folio + ", fecha=" + fecha + ", total=" + total + ", proveedor=" + proveedor + ", detalles=" + detalles + '}';
    }
}
