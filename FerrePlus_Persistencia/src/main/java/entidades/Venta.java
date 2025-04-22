/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Representa una venta realizada en el sistema.
 * Contiene información sobre el folio, fecha y hora de la venta, total, estado (activa/cancelada),
 * el ID de la caja en la que se realizó la venta y una lista de los detalles de la venta
 * (los productos vendidos).
 * @author Beto_
 */
public class Venta implements Serializable{
    private ObjectId id;
    private String folio;
    private LocalDateTime fechaHora;
    private Double total;
    private Boolean estado;
    private ObjectId idCaja;
    private List<DetalleVenta> detalles;

    public Venta() {
        this.detalles = new ArrayList<>();
    }

    public Venta(String folio, LocalDateTime fechaHora, Double total, ObjectId idCaja) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.idCaja = idCaja;
        this.detalles = new ArrayList<>();
    }

    public Venta(String folio, LocalDateTime fechaHora, Double total, ObjectId idCaja, List<DetalleVenta> detalles) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = true;
        this.idCaja = idCaja;
        this.detalles = detalles;
    }
    
    public Venta(String folio, LocalDateTime fechaHora, Double total, Boolean estado, ObjectId idCaja, List<DetalleVenta> detalles) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = estado;
        this.idCaja = idCaja;
        this.detalles = detalles;
    }

    public Venta(ObjectId id, String folio, LocalDateTime fechaHora, Double total, Boolean estado, ObjectId idCaja, List<DetalleVenta> detalles) {
        this.id = id;
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = estado;
        this.idCaja = idCaja;
        this.detalles = detalles;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public ObjectId getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(ObjectId idCaja) {
        this.idCaja = idCaja;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Venta{" + "id=" + id + ", folio=" + folio + ", fechaHora=" + fechaHora + ", total=" + total + ", estado=" + estado + ", idCaja=" + idCaja + ", detalles=" + detalles + '}';
    }
    
    /**
     * Representa la información detallada de un producto dentro de una venta.
     * Incluye el ID del producto vendido, la cantidad, el descuento aplicado
     * y el subtotal correspondiente a ese detalle.
     */
    public static class DetalleVenta implements Serializable{
        private ObjectId idProducto;
        private Integer cantidad;
        private Double descuento;
        private Double subtotal;

        public DetalleVenta() {
        }

        public DetalleVenta(ObjectId idProducto, Integer cantidad, Double descuento, Double subtotal) {
            this.idProducto = idProducto;
            this.cantidad = cantidad;
            this.descuento = descuento;
            this.subtotal = subtotal;
        }

        public ObjectId getIdProducto() {
            return idProducto;
        }

        public void setIdProducto(ObjectId idProducto) {
            this.idProducto = idProducto;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public Double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(Double subtotal) {
            this.subtotal = subtotal;
        }

        public Double getDescuento() {
            return descuento;
        }

        public void setDescuento(Double descuento) {
            this.descuento = descuento;
        }

        @Override
        public String toString() {
            return "DetalleVenta{" + "idProducto=" + idProducto + ", cantidad=" + cantidad + ", subtotal=" + subtotal + ", descuento=" + descuento + '}';
        }
    }
}
