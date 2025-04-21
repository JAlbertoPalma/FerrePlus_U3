/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nuevosDTOs;

/**
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
