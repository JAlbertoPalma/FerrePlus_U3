/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import org.bson.types.ObjectId;

/**
 *
 * @author Beto_
 */
public class DetalleCompra implements Serializable{
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
