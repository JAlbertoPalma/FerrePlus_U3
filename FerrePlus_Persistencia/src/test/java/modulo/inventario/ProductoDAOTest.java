/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package modulo.inventario;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import conexion.Conexion;
import entidades.Producto;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.BsonObjectId;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Beto_
 */
public class ProductoDAOTest {
    private IProductoDAO productoDAO;
    private Producto producto1;
    private Producto producto2;

    @BeforeEach
    void setUp() throws PersistenciaException {
        productoDAO = ProductoDAO.getInstanceDAO();

        producto1 = new Producto(
            "001", "Clavos", "General", "Kilos", 13.0, 50.0,
            "Herreros Fregones OBSON", 50, LocalDateTime.now(), true, "Nadap"
        );

        producto2 = new Producto(
            "002", "Tornillos", "General", "Kilos", 10.0, 45.0,
            "Herreros Fregones OBSON", 40, LocalDateTime.now(), true, "Nadaa"
        );

        productoDAO.agregar(producto1);
        productoDAO.agregar(producto2);
    }

    @AfterEach
    void tearDown() throws PersistenciaException {
        productoDAO.eliminar(producto1.getId().toHexString());
        productoDAO.eliminar(producto2.getId().toHexString());
    }
    
    ////////////////////PRUEBAS POSITIVAS//////////////////////////////
    @Test
    void testAgregarProductos() throws PersistenciaException {
        Producto nuevoProducto = new Producto(
            "003", "Martillos", "General", "Piezas", 20.0, 70.0,
            "Proveedor XYZ", 25, LocalDateTime.now(), true, "NadaX"
        );

        Producto agregado = productoDAO.agregar(nuevoProducto);
        assertNotNull(agregado.getId());

        productoDAO.eliminar(agregado.getId().toHexString());
    }

    @Test
    void testActualizarProductos() throws PersistenciaException {
        producto1.setEstado(false);
        Producto actualizado = productoDAO.actualizar(producto1);
        assertFalse(actualizado.getEstado());
    }

    @Test
    void testObtenerPorId() throws PersistenciaException {
        Producto producto = productoDAO.obtenerPorId(producto1.getId().toHexString());
        assertNotNull(producto);
        assertEquals(producto1.getSku(), producto.getSku());
    }

    @Test
    void testObtenerPorSKU() throws PersistenciaException {
        Producto producto = productoDAO.obtenerPorSKU("002");
        assertNotNull(producto);
        assertEquals("Tornillos", producto.getNombre());
    }

    @Test
    void testObtenerTodos() throws PersistenciaException {
        List<Producto> productos = productoDAO.obtenerTodos();
        assertNotNull(productos);
        assertTrue(productos.size() >= 2);
    }

    @Test
    void testObtenerPorFiltros() throws PersistenciaException {
        List<Producto> productos = productoDAO.obtenerPorFiltros(null, null, null);
        assertNotNull(productos);
        assertTrue(productos.size() >= 2);
    }

    @Test
    void testEliminarProducto() throws PersistenciaException {
        Producto productoEliminar = new Producto(
            "004", "Llaves", "Herramientas", "Piezas", 30.0, 80.0,
            "Proveedor ABC", 20, LocalDateTime.now(), true, "NadaY"
        );
        productoDAO.agregar(productoEliminar);

        boolean eliminado = productoDAO.eliminar(productoEliminar.getId().toHexString());
        assertTrue(eliminado);
    }
    
    ////////////////////PRUEBAS NEGATIVAS//////////////////////////////
    @Test
    void testAgregarProductoNull() {
        assertThrows(PersistenciaException.class, () -> {
            productoDAO.agregar(null);
        });
    }

    @Test
    void testActualizarProductoNoExistente() {
        Producto inexistente = new Producto(
            "999", "Fantasma", "N/A", "Piezas", 0.0, 0.0,
            "Nadie", 0, LocalDateTime.now(), true, "Nada"
        );
        assertThrows(PersistenciaException.class, () -> {
            productoDAO.actualizar(inexistente);
        });
    }

    @Test
    void testObtenerPorIdInvalido() throws PersistenciaException {
        assertThrows(PersistenciaException.class, () -> {
            Producto producto = productoDAO.obtenerPorId("invalidoID");
        });
    }

    @Test
    void testObtenerPorSKUInvalido() throws PersistenciaException {
        Producto producto = productoDAO.obtenerPorSKU("999");
        assertNull(producto);
    }

    @Test
    void testObtenerPorFiltrosSinResultados() throws PersistenciaException {
        List<Producto> productos = productoDAO.obtenerPorFiltros("Inexistente", null, null);
        assertNotNull(productos);
        assertEquals(0, productos.size());
    }

    @Test
    void testEliminarProductoNoExistente() throws PersistenciaException {
        boolean eliminado = productoDAO.eliminar("507f1f77bcf86cd799439011"); // ID ficticio
        assertFalse(eliminado);
    }

    @Test
    void testEliminarProductoConIdInvalido() {
        assertThrows(PersistenciaException.class, () -> {
            productoDAO.eliminar("id-invalido");
        });
    }
}
