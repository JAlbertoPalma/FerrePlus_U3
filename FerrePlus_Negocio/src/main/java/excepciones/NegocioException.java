/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepción personalizada que se lanza para indicar errores
 * que ocurren en la capa de lógica de negocio de la aplicación.
 * Esta excepción se utiliza para señalar condiciones o reglas
 * de negocio que no se cumplen durante la ejecución de las operaciones.
 * 
 * @author Beto_
 */
public class NegocioException extends Exception{

    /**
     * Constructor por defecto de la excepción NegocioException.
     */
    public NegocioException() {
    }
    
    /**
     * Constructor de NegocioException que permite especificar un mensaje
     * descriptivo del error de negocio ocurrido.
     *
     * @param message El mensaje que describe la excepción de negocio.
     */
    public NegocioException(String message) {
        super(message);
    }
    
    /**
     * Constructor de NegocioException que permite especificar un mensaje
     * descriptivo del error y la causa original de la excepción.
     *
     * @param message El mensaje que describe la excepción de negocio.
     * @param cause   La excepción que causó esta excepción de negocio.
     */
    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}
