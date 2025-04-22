/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Representa una compra realizada a un proveedor.
 * Contiene información sobre el folio, fecha de la compra, total, proveedor
 * y una lista de los detalles de la compra (los productos adquiridos).
 * @author Beto_
 */
public class Compra implements Serializable{
    private ObjectId id;
    private String folio;
    private LocalDate fecha;
    private Double total;
    private String proveedor;
    private List<DetalleCompra> detalles;

    public Compra() {
        this.detalles = new ArrayList<>();
    }

    public Compra(String folio, LocalDate fecha, Double total, String proveedor) {
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
        this.proveedor = proveedor;
        this.detalles = new ArrayList<>();
    }
    
    public Compra(String folio, LocalDate fecha, Double total, String proveedor, List<DetalleCompra> detalles) {
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
        this.proveedor = proveedor;
        this.detalles = detalles;
    }

    public Compra(ObjectId id, String folio, LocalDate fecha, Double total, String proveedor, List<DetalleCompra> detalles) {
        this.id = id;
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
        this.proveedor = proveedor;
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

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public List<DetalleCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCompra> detalles) {
        this.detalles = detalles;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    @Override
    public String toString() {
        return "Compra{" + "id=" + id + ", folio=" + folio + ", fecha=" + fecha + ", total=" + total + ", proveedor=" + proveedor + ", detalles=" + detalles + '}';
    }
    
    /**
     * Representa la información detallada de un producto dentro de una compra.
     * Incluye el ID del producto, la cantidad comprada, el precio de compra unitario
     * y el subtotal correspondiente a ese detalle.
     */
    public static class DetalleCompra implements Serializable {
        private ObjectId idProducto;
        private Integer cantidad;
        private Double precioDeCompra;
        private Double subtotal;

        public DetalleCompra() {
        }

        public DetalleCompra(ObjectId idProducto, Integer cantidad, Double precioDeCompra, Double subtotal) {
            this.idProducto = idProducto;
            this.cantidad = cantidad;
            this.precioDeCompra = precioDeCompra;
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
            return "DetalleCompra{" + "idProducto=" + idProducto + ", cantidad=" + cantidad + ", precioDeCompra=" + precioDeCompra + ", subtotal=" + subtotal + '}';
        }
    }
}
