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
    private static MongoClient mongoClient = null;
    private static final String URL = "mongodb+srv://jesuspalma247910:jNEKUmP0oP6Cbfma@cluster0.py6kelp.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DATABASE_NAME = "FerrePlus";
    
    /**
     * Evitamos instanciar la conexión con
     * un constructor privado
     */
    private Conexion(){
    }
    
    public static MongoDatabase getDatabase(){
        if(mongoClient == null){
            //1. Configuramos el CodecRegistry para habilitar el soporte de POJOs
            PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
                    CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
                    CodecRegistry codecRegistry = fromRegistries(defaultCodecRegistry, fromProviders(pojoCodecProvider));
            
            //2. Configuramos los ajustes del cliente MongoDB
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(new com.mongodb.ConnectionString(URL))
                    .codecRegistry(codecRegistry)
                    .build();
            
            //3. Asignamos los ajustes al MongoClientestático de la clase
            mongoClient = MongoClients.create(clientSettings);
            
            //4. Retornamos la base de datos con la configuración codecs
            System.out.println("Base de datos utilizada: " + DATABASE_NAME);
            return mongoClient.getDatabase(DATABASE_NAME).withCodecRegistry(codecRegistry);
            
        }
        return mongoClient.getDatabase(DATABASE_NAME);
    }
    
//    /**
//     * Método de acceso a la base de datos
//     * desde la clase estática SingletonHelper
//     * @return La conexión MongoBD
//     */
//    public static Conexion getInstance(){
//        return SingletonHelper.INSTANCE;
//    }
//    
//    /**
//     * Método de acceso para el cliente de Mongo
//     * @return El cliente de MongoDB
//     */
//    public MongoClient getMongoClient() {
//        return SingletonHelper.mongoClient;
//    }
//    
//    /**
//     * Se obtiene la base de datos a manejar
//     * en este caso, la de FerrePlus
//     * @return La base de datos utilizada
//     */
//    public MongoDatabase getDatabase() {
//        return SingletonHelper.database;
//    }
//    
//    /**
//     * Cierra la conexión MongoDB y tanto el
//     * cliente como la base de datos pasan a 
//     * ser nulos
//     */
//    public void cerrarConexion() {
//        if (SingletonHelper.mongoClient != null) {
//            SingletonHelper.mongoClient.close();
//            SingletonHelper.mongoClient = null;
//            SingletonHelper.database = null;
//            System.out.println("Conexión cerrada");
//        }
//    }
//    
//    /**
//     * Clase statica anidada cuya función es ser auxiliar para obtener la base de datos
//     * Implementando el patrón singleton "Initialization-on-demand holder idiom"
//     * Para el sistema actual no es necesaria, sin embargo se espera que este sistema se extienda
//     * y su limitación no sea solo una caja, por lo que debe ser segura para hilos (Threath safe)
//     */
//    private static class SingletonHelper{
//        private static final Conexion INSTANCE = new Conexion();
//        private static final String connectionString = "mongodb+srv://jesuspalma247910:jNEKUmP0oP6Cbfma@cluster0.py6kelp.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
//        private static final String nombreDatabase = "FerrePlus";
//        private static MongoClient mongoClient = null;
//        private static MongoDatabase database= null;
//        
//        static { //Deeum no sabía que se podía esto :0
//            //0. Desactivamos los logs de mongo para que no salga en el output
//            Logger.getLogger("org.mongo.driver").setLevel(Level.SEVERE);
//            
//            //1. Configuramos el ServerApi que se ocupa para los settings
//            ServerApi serverApi = ServerApi.builder()
//                    .version(ServerApiVersion.V1)
//                    .build();
//            
//            //2. Configuramos el CodecRegistry para habilitar el soporte de POJOs
//            PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
//            CodecRegistry pojoCodecRegistry = fromProviders(pojoCodecProvider);
//            
//            //3. Configuramos los ajustes del cliente MongoDB
//            MongoClientSettings settings = MongoClientSettings.builder()
//                    .applyConnectionString(new ConnectionString(connectionString))
//                    .serverApi(serverApi)
//                    .build();
//             
//            try{
//                //4. Asignamos los ajustes al MongoClientestático de la clase
//                mongoClient = MongoClients.create(settings);
//                
//                //5. Obtenemos el CodecRegistry del MongoClient
//                CodecRegistry defaultCodecRegistry = mongoClient.getCodecRegistry();
//                
//                //6. Combinamos los CodecRegistry
//                CodecRegistry codecRegistry = fromRegistries(defaultCodecRegistry, pojoCodecRegistry);
//                
//                //7. Reconstruimos el settings con el CodecRegistry combinado
//                settings = MongoClientSettings.builder()
//                .applyConnectionString(new ConnectionString(connectionString))
//                .serverApi(serverApi)
//                .codecRegistry(codecRegistry)
//                .build();
//                
//                //8. Recreamos el MongoClient con los settings actualizados
//                mongoClient = MongoClients.create(settings);
//                
//                //9. Recreamos el MongoClient con los settings actualizados
//                //Pings la base de datos para confrimar que la conexión esta bien
//                database = mongoClient.getDatabase(nombreDatabase);
//                database.runCommand(new Document("ping", 1));
//                System.out.println("Conexión establecida exitosamente");
//                database = mongoClient.getDatabase(nombreDatabase);
//                System.out.println("Usando: " + nombreDatabase);
//            }catch(MongoException me){
//                System.out.println("Error al conectar MongoBD Atlas: " + me.getMessage());
//            }
//        } 
//    }
}
