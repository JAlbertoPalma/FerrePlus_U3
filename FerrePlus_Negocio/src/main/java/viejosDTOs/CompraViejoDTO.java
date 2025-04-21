/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viejosDTOs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Beto_
 */
public class CompraViejoDTO {
    private String id;
    private String folio;
    private LocalDate fecha;
    private Double total;
    private String proveedor;
    private List<DetalleCompraViejoDTO> detalles;

    public CompraViejoDTO() {
        detalles = new ArrayList<>();
    }

    public CompraViejoDTO(String id, String folio, LocalDate fecha, Double total, String proveedor, List<DetalleCompraViejoDTO> detalles) {
        this.id = id;
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
        this.proveedor = proveedor;
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

    public List<DetalleCompraViejoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCompraViejoDTO> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "CompraViejoDTO{" + "id=" + id + ", folio=" + folio + ", fecha=" + fecha + ", total=" + total + ", proveedor=" + proveedor + ", detalles=" + detalles + '}';
    }
    
    public static class DetalleCompraViejoDTO {
        private String idProducto;
        private Integer cantidad;
        private Double precioDeCompra;
        private Double subtotal;

        public DetalleCompraViejoDTO() {
        }

        public DetalleCompraViejoDTO(String idProducto, Integer cantidad, Double precioDeCompra, Double subtotal) {
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
            return "DetalleCompraViejoDTO{" + "idProducto=" + idProducto + ", cantidad=" + cantidad + ", precioDeCompra=" + precioDeCompra + ", subtotal=" + subtotal + '}';
        }
    }
}
