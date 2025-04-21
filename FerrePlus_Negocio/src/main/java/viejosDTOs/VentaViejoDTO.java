/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viejosDTOs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Beto_
 */
public class VentaViejoDTO {
    private String id;
    private String folio;
    private LocalDateTime fechaHora;
    private Double total;
    private Boolean estado;
    private String idCaja;
    private List<DetalleVentaViejoDTO> detalles;

    public VentaViejoDTO() {
        detalles = new ArrayList<>();
    }

    public VentaViejoDTO(String id, String folio, LocalDateTime fechaHora, Double total, Boolean estado, String idCaja, List<DetalleVentaViejoDTO> detalles) {
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

    public List<DetalleVentaViejoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaViejoDTO> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "VentaViejoDTO{" + "id=" + id + ", folio=" + folio + ", fechaHora=" + fechaHora + ", total=" + total + ", estado=" + estado + ", idCaja=" + idCaja + ", detalles=" + detalles + '}';
    }
    
    public static class DetalleVentaViejoDTO {
        private String idProducto;
        private Integer cantidad;
        private Double descuento;
        private Double subtotal;

        public DetalleVentaViejoDTO() {
        }

        public DetalleVentaViejoDTO(String idProducto, Integer cantidad, Double descuento, Double subtotal) {
            this.idProducto = idProducto;
            this.cantidad = cantidad;
            this.descuento = descuento;
            this.subtotal = subtotal;
        }

        public String getIdProducto() {
            return idProducto;
        }

        public void setIdProducto(String idProducto) {
            this.idProducto = idProducto;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public Double getDescuento() {
            return descuento;
        }

        public void setDescuento(Double descuento) {
            this.descuento = descuento;
        }

        public Double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(Double subtotal) {
            this.subtotal = subtotal;
        }

        @Override
        public String toString() {
            return "DetalleVentaViejoDTO{" + "idProducto=" + idProducto + ", cantidad=" + cantidad + ", descuento=" + descuento + ", subtotal=" + subtotal + '}';
        }
    }
}
