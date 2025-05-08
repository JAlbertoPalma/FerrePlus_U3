/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Representa una venta realizada en el sistema.
 * Contiene información sobre el folio, fecha y hora de la venta, total, estado (activa/cancelada),
 * el ID de la caja en la que se realizó la venta y una lista de los detalles de la venta
 * (los productos vendidos).
 * @author Beto_
 */
public class Venta implements Serializable{
    private ObjectId id;
    private String folio;
    private LocalDateTime fechaHora;
    private Double total;
    private Boolean estado;
    private ObjectId idCaja;
    private List<DetalleVenta> detalles;

    public Venta() {
        this.detalles = new ArrayList<>();
    }

    public Venta(String folio, LocalDateTime fechaHora, Double total, ObjectId idCaja) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.idCaja = idCaja;
        this.detalles = new ArrayList<>();
    }

    public Venta(String folio, LocalDateTime fechaHora, Double total, ObjectId idCaja, List<DetalleVenta> detalles) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = true;
        this.idCaja = idCaja;
        this.detalles = detalles;
    }
    
    public Venta(String folio, LocalDateTime fechaHora, Double total, Boolean estado, ObjectId idCaja, List<DetalleVenta> detalles) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = estado;
        this.idCaja = idCaja;
        this.detalles = detalles;
    }

    public Venta(ObjectId id, String folio, LocalDateTime fechaHora, Double total, Boolean estado, ObjectId idCaja, List<DetalleVenta> detalles) {
        this.id = id;
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = estado;
        this.idCaja = idCaja;
        this.detalles = detalles;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public ObjectId getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(ObjectId idCaja) {
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
        return "Venta{" + "id=" + id + ", folio=" + folio + ", fechaHora=" + fechaHora + ", total=" + total + ", estado=" + estado + ", idCaja=" + idCaja + ", detalles=" + detalles + '}';
    }
}
