/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 * Data Transfer Object (DTO) para la apertura de una nueva caja.
 * Contiene la información necesaria para iniciar una sesión de caja,
 * como el monto inicial. Otros detalles se gestionan al momento de la apertura.
 * 
 * @author Beto_
 */
public class CajaNuevoDTO {
    //apertura caja
    private Double montoInicial;

    public CajaNuevoDTO() {
    }

    public CajaNuevoDTO(Double montoInicial) {
        this.montoInicial = montoInicial;
    }

    public Double getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(Double montoInicial) {
        this.montoInicial = montoInicial;
    }

    @Override
    public String toString() {
        return "CajaNuevaDTO{" + "montoInicial=" + montoInicial + '}';
    }
    
}
