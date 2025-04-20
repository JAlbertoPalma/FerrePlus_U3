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
    //apertura caja
    private ObjectId id;
    private LocalDateTime fechaHoraApertura;
    private Double montoInicial;
    private Boolean estadoSesion;
    
    //Resumen de ventas
    private Double totalVentas;
    private Integer cantidadDeProductos;
    private Integer numeroDeVentas;
    
    //Cierre caja
    private LocalDateTime fechaHoraCierre;
    private Double montoFinalEstimado;
    private String observacionesCierre;

    public Caja() {
    }
    
    /**
     * Constructor exclusivo para abrir caja
     * @param montoInicial el monto con el que se inicia la caja
     */
    public Caja(Double montoInicial) {
        this.fechaHoraApertura = LocalDateTime.now();
        this.montoInicial = montoInicial;
        this.estadoSesion = true;
        this.totalVentas = 0.0;
        this.cantidadDeProductos = 0;
        this.numeroDeVentas = 0;
        this.fechaHoraCierre = null;
        this.montoFinalEstimado = montoInicial;
        this.observacionesCierre = null;
    }

    public Caja(LocalDateTime fechaHoraApertura, Double montoInicial, Boolean estadoSesion, Double totalVentas, Integer cantidadDeProductos, Integer numeroDeVentas, LocalDateTime fechaHoraCierre, Double montoFinalEstimado, String observacionesCierre) {
        this.fechaHoraApertura = fechaHoraApertura;
        this.montoInicial = montoInicial;
        this.estadoSesion = estadoSesion;
        this.totalVentas = totalVentas;
        this.cantidadDeProductos = cantidadDeProductos;
        this.numeroDeVentas = numeroDeVentas;
        this.fechaHoraCierre = fechaHoraCierre;
        this.montoFinalEstimado = montoFinalEstimado;
        this.observacionesCierre = observacionesCierre;
    }

    public Caja(ObjectId id, LocalDateTime fechaHoraApertura, Double montoInicial, Boolean estadoSesion, Double totalVentas, Integer cantidadDeProductos, Integer numeroDeVentas, LocalDateTime fechaHoraCierre, Double montoFinalEstimado, String observacionesCierre) {
        this.id = id;
        this.fechaHoraApertura = fechaHoraApertura;
        this.montoInicial = montoInicial;
        this.estadoSesion = estadoSesion;
        this.totalVentas = totalVentas;
        this.cantidadDeProductos = cantidadDeProductos;
        this.numeroDeVentas = numeroDeVentas;
        this.fechaHoraCierre = fechaHoraCierre;
        this.montoFinalEstimado = montoFinalEstimado;
        this.observacionesCierre = observacionesCierre;
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

    public Double getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(Double montoInicial) {
        this.montoInicial = montoInicial;
    }

    public Boolean getEstadoSesion() {
        return estadoSesion;
    }

    public void setEstadoSesion(Boolean estadoSesion) {
        this.estadoSesion = estadoSesion;
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

    public LocalDateTime getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraCierre(LocalDateTime fechaHoraCierre) {
        this.fechaHoraCierre = fechaHoraCierre;
    }

    public Double getMontoFinalEstimado() {
        return montoFinalEstimado;
    }

    public void setMontoFinalEstimado(Double montoFinalEstimado) {
        this.montoFinalEstimado = montoFinalEstimado;
    }

    public String getObservacionesCierre() {
        return observacionesCierre;
    }

    public void setObservacionesCierre(String observacionesCierre) {
        this.observacionesCierre = observacionesCierre;
    }

    @Override
    public String toString() {
        return "Caja{" + "id=" + id + ", fechaHoraApertura=" + fechaHoraApertura + ", montoInicial=" + montoInicial + ", estadoSesion=" + estadoSesion + ", totalVentas=" + totalVentas + ", cantidadDeProductos=" + cantidadDeProductos + ", numeroDeVentas=" + numeroDeVentas + ", fechaHoraCierre=" + fechaHoraCierre + ", montoFinalEstimado=" + montoFinalEstimado + ", observacionesCierre=" + observacionesCierre + '}';
    }
}
