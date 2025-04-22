/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepción personalizada que se lanza cuando ocurre un error
 * durante las operaciones de persistencia de datos.
 * Esta excepción encapsula problemas que surgen al interactuar
 * con la base de datos o cualquier otro mecanismo de almacenamiento.
 * @author Beto_
 */
public class PersistenciaException extends Exception{

    /**
     * Constructor por defecto de la excepción PersistenciaException.
     */
    public PersistenciaException() {
    }
    
    /**
     * Constructor de PersistenciaException que permite especificar un mensaje
     * descriptivo del error ocurrido.
     *
     * @param message El mensaje que describe la excepción.
     */
    public PersistenciaException(String message) {
        super(message);
    }
    
    /**
     * Constructor de PersistenciaException que permite especificar un mensaje
     * descriptivo del error y la causa original de la excepción.
     *
     * @param message La mensaje que describe la excepción.
     * @param cause   La excepción que causó esta excepción.
     */
    public PersistenciaException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
