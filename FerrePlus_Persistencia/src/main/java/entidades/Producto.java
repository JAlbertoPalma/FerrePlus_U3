/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;

/**
 *
 * @author Beto_
 */
public class Producto implements Serializable{
    private ObjectId id;
    private String sku;
    private String nombre;
    private String categoria;
    private String unidadMedida;
    private Double precioCompraReferencial;
    private Double precioVenta;
    private String proveedor;
    private Integer stock;
    private LocalDateTime fechaHoraAlta;
    private Boolean estado;
    private String observaciones;

    public Producto() {
    }
    
    public Producto(String SKU, String nombre, String categoria, String unidadMedida, Double precioCompraReferencial, Double precioVenta, String proveedor, Integer stock, LocalDateTime fechaHoraAlta, Boolean estado, String observaciones) {
        this.sku = SKU;
        this.nombre = nombre;
        this.categoria = categoria;
        this.unidadMedida = unidadMedida;
        this.precioCompraReferencial = precioCompraReferencial;
        this.precioVenta = precioVenta;
        this.proveedor = proveedor;
        this.stock = stock;
        this.fechaHoraAlta = fechaHoraAlta;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public Producto(ObjectId id, String SKU, String nombre, String categoria, String unidadMedida, Double precioCompraReferencial, Double precioVenta, String proveedor, Integer stock, LocalDateTime fechaHoraAlta, Boolean estado, String observaciones) {
        this.id = id;
        this.sku = SKU;
        this.nombre = nombre;
        this.categoria = categoria;
        this.unidadMedida = unidadMedida;
        this.precioCompraReferencial = precioCompraReferencial;
        this.precioVenta = precioVenta;
        this.proveedor = proveedor;
        this.stock = stock;
        this.fechaHoraAlta = fechaHoraAlta;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Double getPrecioCompraReferencial() {
        return precioCompraReferencial;
    }

    public void setPrecioCompraReferencial(Double precioCompraReferencial) {
        this.precioCompraReferencial = precioCompraReferencial;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public LocalDateTime getFechaHoraAlta() {
        return fechaHoraAlta;
    }

    public void setFechaHoraAlta(LocalDateTime fechaHoraAlta) {
        this.fechaHoraAlta = fechaHoraAlta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", SKU=" + sku + ", nombre=" + nombre + ", categoria=" + categoria + ", unidadMedida=" + unidadMedida + ", precioCompraReferencial=" + precioCompraReferencial + ", precioVenta=" + precioVenta + ", proveedor=" + proveedor + ", stock=" + stock + ", fechaHoraAlta=" + fechaHoraAlta + ", estado=" + estado + ", observaciones=" + observaciones + '}';
    }
}
