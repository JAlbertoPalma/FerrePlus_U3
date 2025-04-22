/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author Beto_
 */
public class Conexion {    
    
    /**
     * Evitamos instanciar la conexión con
     * un constructor privado
     */
    private Conexion(){
    }
    
    /**
     * Método de acceso a la base de datos
     * desde la clase estática SingletonHelper
     * @return La conexión MongoBD
     */
    public static Conexion getInstance(){
        return SingletonHelper.INSTANCE;
    }
    
    /**
     * Método de acceso para el cliente de Mongo
     * @return El cliente de MongoDB
     */
    public MongoClient getMongoClient() {
        return SingletonHelper.mongoClient;
    }
    
    /**
     * Se obtiene la base de datos a manejar
     * en este caso, la de FerrePlus
     * @return La base de datos utilizada
     */
    public MongoDatabase getDatabase() {
        return SingletonHelper.database;
    }
    
    /**
     * Cierra la conexión MongoDB y tanto el
     * cliente como la base de datos pasan a 
     * ser nulos
     */
    public void cerrarConexion() {
        if (SingletonHelper.mongoClient != null) {
            SingletonHelper.mongoClient.close();
            SingletonHelper.mongoClient = null;
            SingletonHelper.database = null;
            System.out.println("Conexión cerrada");
        }
    }
    
    /**
     * Clase statica anidada cuya función es ser auxiliar para obtener la base de datos
     * Implementando el patrón singleton "Initialization-on-demand holder idiom"
     * Para el sistema actual no es necesaria, sin embargo se espera que este sistema se extienda
     * y su limitación no sea solo una caja, por lo que debe ser segura para hilos (Threath safe)
     */
    private static class SingletonHelper{
        private static final Conexion INSTANCE = new Conexion();
        private static final String connectionString = "mongodb+srv://jesuspalma247910:jNEKUmP0oP6Cbfma@cluster0.py6kelp.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        private static final String nombreDatabase = "FerrePlus";
        private static MongoClient mongoClient = null;
        private static MongoDatabase database= null;
        
        static { //Deeum no sabía que se podía esto :0
            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();
            
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .serverApi(serverApi)
                    .build();
            
            try{
                mongoClient = MongoClients.create(settings);
                MongoDatabase adminDatabase = mongoClient.getDatabase("admin");
                adminDatabase.runCommand(new Document("ping", 1));
                System.out.println("Conexión establecida exitosamente");
                database = mongoClient.getDatabase(nombreDatabase);
                System.out.println("Usando: " + nombreDatabase);
            }catch(MongoException me){
                System.out.println("Error al conectar MongoBD Atlas");
            }
        } 
    }
}
