/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ferreplus_persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import conexion.Conexion;

/**
 *
 * @author Beto_
 */
public class FerrePlus_Persistencia {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        MongoClient cliente1 = Conexion.getInstance().getMongoClient();
        MongoDatabase mongobd1 = Conexion.getInstance().getDatabase();
        
        MongoClient cliente2 = Conexion.getInstance().getMongoClient();
        MongoDatabase mongobd2 = Conexion.getInstance().getDatabase();
        
        System.out.println("Prueba de instancia de cliente única: " + (cliente1 == cliente2));
        System.out.println("Prueba de instancia de bd única: " + (mongobd1 == mongobd2));
        
        //cerramos conexion
        Conexion.getInstance().cerrarConexion();
    }
}
