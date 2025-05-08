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
}
