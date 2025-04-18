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
public class Caja implements Serializable{
    private ObjectId id;
    private LocalDateTime fechaHoraApertura;
    private LocalDateTime fechaHoraCierre;
    private Double totalVentas;
    private Integer cantidadDeProductos;
    private Integer numeroDeVentas;
    private Double montoInicial;
    private String estadoSesion;
    private String observaciones;

    public Caja() {
    }

    public Caja(LocalDateTime fechaHoraApertura, LocalDateTime fechaHoraCierre, Double totalVentas, Integer cantidadDeProductos, Integer numeroDeVentas, Double montoInicial, String estadoSesion, String observaciones) {
        this.fechaHoraApertura = fechaHoraApertura;
        this.fechaHoraCierre = fechaHoraCierre;
        this.totalVentas = totalVentas;
        this.cantidadDeProductos = cantidadDeProductos;
        this.numeroDeVentas = numeroDeVentas;
        this.montoInicial = montoInicial;
        this.estadoSesion = estadoSesion;
        this.observaciones = observaciones;
    }

    public Caja(ObjectId id, LocalDateTime fechaHoraApertura, LocalDateTime fechaHoraCierre, Double totalVentas, Integer cantidadDeProductos, Integer numeroDeVentas, Double montoInicial, String estadoSesion, String observaciones) {
        this.id = id;
        this.fechaHoraApertura = fechaHoraApertura;
        this.fechaHoraCierre = fechaHoraCierre;
        this.totalVentas = totalVentas;
        this.cantidadDeProductos = cantidadDeProductos;
        this.numeroDeVentas = numeroDeVentas;
        this.montoInicial = montoInicial;
        this.estadoSesion = estadoSesion;
        this.observaciones = observaciones;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public LocalDateTime getFechaHoraApertura() {
        return fechaHoraApertura;
    }

    public void setFechaHoraApertura(LocalDateTime fechaHoraApertura) {
        this.fechaHoraApertura = fechaHoraApertura;
    }

    public LocalDateTime getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraCierre(LocalDateTime fechaHoraCierre) {
        this.fechaHoraCierre = fechaHoraCierre;
    }

    public Double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(Double totalVentas) {
        this.totalVentas = totalVentas;
    }

    public Integer getCantidadDeProductos() {
        return cantidadDeProductos;
    }

    public void setCantidadDeProductos(Integer cantidadDeProductos) {
        this.cantidadDeProductos = cantidadDeProductos;
    }

    public Integer getNumeroDeVentas() {
        return numeroDeVentas;
    }

    public void setNumeroDeVentas(Integer numeroDeVentas) {
        this.numeroDeVentas = numeroDeVentas;
    }

    public Double getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(Double montoInicial) {
        this.montoInicial = montoInicial;
    }

    public String getEstadoSesion() {
        return estadoSesion;
    }

    public void setEstadoSesion(String estadoSesion) {
        this.estadoSesion = estadoSesion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Caja{" + "id=" + id + ", fechaHoraApertura=" + fechaHoraApertura + ", fechaHoraCierre=" + fechaHoraCierre + ", totalVentas=" + totalVentas + ", cantidadDeProductos=" + cantidadDeProductos + ", numeroDeVentas=" + numeroDeVentas + ", montoInicial=" + montoInicial + ", estadoSesion=" + estadoSesion + ", observaciones=" + observaciones + '}';
    }
    
}
