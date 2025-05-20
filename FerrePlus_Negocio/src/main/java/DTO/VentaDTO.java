/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) que contiene la información completa de una venta,
 * incluyendo su identificador único y la lista de detalles de los productos vendidos.
 * Se utiliza para transferir datos de ventas existentes entre las capas de la aplicación.
 * 
 * @author Beto_
 */
public class VentaDTO {
    private String id;
    private String folio;
    private LocalDateTime fechaHora;
    private Double total;
    private Boolean estado;
    private String idCaja;
    private List<DetalleVentaDTO> detalles;

    public VentaDTO() {
        detalles = new ArrayList<>();
    }

    public VentaDTO(String id, String folio, LocalDateTime fechaHora, Double total, Boolean estado, String idCaja, List<DetalleVentaDTO> detalles) {
        this.id = id;
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = estado;
        this.idCaja = idCaja;
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(String idCaja) {
        this.idCaja = idCaja;
    }

    public List<DetalleVentaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaDTO> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "VentaDTO{" + "id=" + id + ", folio=" + folio + ", fechaHora=" + fechaHora + ", total=" + total + ", estado=" + estado + ", idCaja=" + idCaja + ", detalles=" + detalles + '}';
    }  
}
