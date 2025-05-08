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
public class DetalleVenta implements Serializable{
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
