/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Beto_
 */
public class DetalleCompraDTO {
    private String idProducto;
    private Integer cantidad;
    private Double precioDeCompra;
    private Double subtotal;

    public DetalleCompraDTO() {
    }

    public DetalleCompraDTO(String idProducto, Integer cantidad, Double precioDeCompra, Double subtotal) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioDeCompra = precioDeCompra;
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
        return "DetalleCompraDTO{" + "idProducto=" + idProducto + ", cantidad=" + cantidad + ", precioDeCompra=" + precioDeCompra + ", subtotal=" + subtotal + '}';
    }
}
