/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Beto_
 */
public class Venta implements Serializable{
    private ObjectId id;
    private LocalDate fechaHora;
    private Double total;
    private ObjectId idCaja;
    private List<DetalleVenta> detalles;

    public Venta() {
    }

    public Venta(LocalDate fechaHora, Double total, ObjectId idCaja, List<DetalleVenta> detalles) {
        this.fechaHora = fechaHora;
        this.total = total;
        this.idCaja = idCaja;
        this.detalles = detalles;
    }

    public Venta(ObjectId id, LocalDate fechaHora, Double total, ObjectId idCaja, List<DetalleVenta> detalles) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.total = total;
        this.idCaja = idCaja;
        this.detalles = detalles;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ObjectId getCajaId() {
        return idCaja;
    }

    public void setCajaId(ObjectId idCaja) {
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
        return "Venta{" + "id=" + id + ", fechaHora=" + fechaHora + ", total=" + total + ", idCaja=" + idCaja + ", detalles=" + detalles + '}';
    }
    
    public static class DetalleVenta implements Serializable{
        private ObjectId idProducto;
        private Integer cantidad;
        private Double subtotal;
        private Double descuento;

        public DetalleVenta() {
        }

        public DetalleVenta(ObjectId idProducto, Integer cantidad, Double subtotal, Double descuento) {
            this.idProducto = idProducto;
            this.cantidad = cantidad;
            this.subtotal = subtotal;
            this.descuento = descuento;
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
