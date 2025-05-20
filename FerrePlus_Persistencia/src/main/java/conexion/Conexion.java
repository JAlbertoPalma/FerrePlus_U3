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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author Beto_
 */
public class Conexion {  
    private static volatile Conexion instance;
    private MongoClient mongoClient;
    private static final String URL = "mongodb+srv://jesuspalma247910:jNEKUmP0oP6Cbfma@cluster0.py6kelp.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DATABASE_NAME = "FerrePlus";

    private Conexion() {
        //1. Configuramos el CodecRegistry para habilitar el soporte de POJOs
        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        CodecRegistry codecRegistry = fromRegistries(defaultCodecRegistry, fromProviders(pojoCodecProvider));

        //2. Configuramos los ajustes del cliente MongoDB
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(new com.mongodb.ConnectionString(URL))
                .codecRegistry(codecRegistry)
                .build();

        //3. Creamos el MongoClient
        this.mongoClient = MongoClients.create(clientSettings);

        //4. Imprimimos la base de datos utilizada
        System.out.println("Base de datos utilizada: " + DATABASE_NAME);
    }

    public static Conexion getInstance() {
        if (instance == null) {
            synchronized (Conexion.class) {
                if (instance == null) {
                    instance = new Conexion();
                }
            }
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return this.mongoClient.getDatabase(DATABASE_NAME);
    }

    public MongoClient getMongoClient() {
        return this.mongoClient;
    }
    
//    private static MongoClient mongoClient = null;
//    private static final String URL = "mongodb+srv://jesuspalma247910:jNEKUmP0oP6Cbfma@cluster0.py6kelp.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
//    private static final String DATABASE_NAME = "FerrePlus";
//    
//    /**
//     * Evitamos instanciar la conexión con
//     * un constructor privado
//     */
//    private Conexion(){
//    }
//    
//    public static MongoDatabase getDatabase(){
//        if(mongoClient == null){
//            //1. Configuramos el CodecRegistry para habilitar el soporte de POJOs
//            PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
//                    CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
//                    CodecRegistry codecRegistry = fromRegistries(defaultCodecRegistry, fromProviders(pojoCodecProvider));
//            
//            //2. Configuramos los ajustes del cliente MongoDB
//            MongoClientSettings clientSettings = MongoClientSettings.builder()
//                    .applyConnectionString(new com.mongodb.ConnectionString(URL))
//                    .codecRegistry(codecRegistry)
//                    .build();
//            
//            //3. Asignamos los ajustes al MongoClientestático de la clase
//            mongoClient = MongoClients.create(clientSettings);
//            
//            //4. Retornamos la base de datos con la configuración codecs
//            System.out.println("Base de datos utilizada: " + DATABASE_NAME);
//            return mongoClient.getDatabase(DATABASE_NAME).withCodecRegistry(codecRegistry);
//            
//        }
//        return mongoClient.getDatabase(DATABASE_NAME);
//    }
}
