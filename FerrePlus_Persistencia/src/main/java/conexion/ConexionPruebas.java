/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.io.IOException;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author Beto_
 */
public class ConexionPruebas {
    private MongoClient mongoClient;
    private static final String URL_PRUEBAS = "mongodb://localhost:27017/test_ferreplus"; // Cambia la URL si es necesario para tus pruebas
    private static final String DATABASE_NAME_PRUEBAS = "test_ferreplus"; // Nombre de la base de datos para pruebas

    public ConexionPruebas() {
        //1. Configuramos el CodecRegistry para habilitar el soporte de POJOs
        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        CodecRegistry codecRegistry = fromRegistries(defaultCodecRegistry, fromProviders(pojoCodecProvider));

        //2. Configuramos los ajustes del cliente MongoDB
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(new com.mongodb.ConnectionString(URL_PRUEBAS))
                .codecRegistry(codecRegistry)
                .build();

        //3. Creamos el MongoClient
        this.mongoClient = MongoClients.create(clientSettings);

        //4. Imprimimos la base de datos utilizada para pruebas
        System.out.println("Base de datos para pruebas utilizada: " + DATABASE_NAME_PRUEBAS);
    }

    public MongoDatabase getDatabase() {
        return this.mongoClient.getDatabase(DATABASE_NAME_PRUEBAS);
    }

    public MongoClient getMongoClient() {
        return this.mongoClient;
    }
}
