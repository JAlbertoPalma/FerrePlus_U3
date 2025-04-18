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
public class Compra implements Serializable{
    private ObjectId id;
    private LocalDate fecha;
    private Double total;
    private ObjectId proveedorId;
    private List<DetalleCompra> detalles;

    public Compra() {
    }

    public Compra(ObjectId id, LocalDate fecha, Double total, ObjectId proveedorId, List<DetalleCompra> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.proveedorId = proveedorId;
        this.detalles = detalles;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public ObjectId getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(ObjectId proveedorId) {
        this.proveedorId = proveedorId;
    }

    public List<DetalleCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCompra> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Compra{" + "id=" + id + ", fecha=" + fecha + ", total=" + total + ", proveedorId=" + proveedorId + ", detalles=" + detalles + '}';
    }
    
    public static class DetalleCompra implements Serializable {
        private ObjectId productoId;
        private Integer cantidad;
        private Double precioDeCompra;
        private Double subtotal;

        public DetalleCompra() {
        }

        public DetalleCompra(ObjectId productoId, Integer cantidad, Double precioDeCompra, Double subtotal) {
            this.productoId = productoId;
            this.cantidad = cantidad;
            this.precioDeCompra = precioDeCompra;
            this.subtotal = subtotal;
        }

        public ObjectId getProductoId() {
            return productoId;
        }

        public void setProductoId(ObjectId productoId) {
            this.productoId = productoId;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public Double getPrecioDeCompra() {
            return precioDeCompra;
        }

        public void setPrecioDeCompra(Double precioDeCompra) {
            this.precioDeCompra = precioDeCompra;
        }

        public Double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(Double subtotal) {
            this.subtotal = subtotal;
        }

        @Override
        public String toString() {
            return "DetalleCompra{" + "productoId=" + productoId + ", cantidad=" + cantidad + ", precioDeCompra=" + precioDeCompra + ", subtotal=" + subtotal + '}';
        }
    }
}
