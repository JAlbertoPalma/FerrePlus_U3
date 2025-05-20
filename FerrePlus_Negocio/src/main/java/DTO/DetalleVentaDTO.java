/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Beto_
 */
public class DetalleVentaDTO {
    private String idProducto;
    private Integer cantidad;
    private Double descuento;
    private Double subtotal;

    public DetalleVentaDTO() {
    }

    public DetalleVentaDTO(String idProducto, Integer cantidad, Double descuento, Double subtotal) {
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
