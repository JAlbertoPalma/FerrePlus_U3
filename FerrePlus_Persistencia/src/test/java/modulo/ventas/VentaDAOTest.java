/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package modulo.ventas;

import entidades.DetalleCompra;
import entidades.DetalleVenta;
import entidades.Venta;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Beto_
 */
public class VentaDAOTest {
    private IVentaDAO ventaDAO;
    private Venta ventaValida;
    private ObjectId productoIdPrueba = new ObjectId("681bd995c1b020138e159a02");

    @BeforeEach
    public void setUp() throws PersistenciaException {
        ventaDAO = VentaDAO.getInstanceDAO();
        List<DetalleVenta> detalles = Arrays.asList(
            new DetalleVenta(productoIdPrueba, 2, 15.0, 30.0)
        );
        ventaValida = new Venta("TESTFOLIO", LocalDateTime.now(), 100.0,
                new ObjectId("681c7271618fd87bdcee6ad1"), detalles);
        ventaDAO.agregar(ventaValida);
    }

    @Test
    public void testAgregarVenta() throws PersistenciaException {
        Venta nuevaVenta = new Venta("FOLIOTEST2", LocalDateTime.now(), 200.0,
                new ObjectId("681c7271618fd87bdcee6ad1"), List.of());
        Venta ventaAgregada = ventaDAO.agregar(nuevaVenta);
        assertNotNull(ventaAgregada.getId(), "El ID de la venta agregada no debería ser nulo");
    }
    
    @Test
    public void testAgregarVentaNula() {
        assertThrows(PersistenciaException.class, () -> {
            ventaDAO.agregar(null);
        });
    }

    @Test
    public void testCancelarVentaExistente() throws PersistenciaException {
        Venta ventaACancelar = new Venta("FOLIOCANCELAR", LocalDateTime.now(), 300.0,
                new ObjectId("681c7271618fd87bdcee6ad1"), List.of());
        ventaDAO.agregar(ventaACancelar);

        Venta ventaCancelada = ventaDAO.cancelar(ventaACancelar.getId().toHexString());
        assertNotNull(ventaCancelada, "Debería devolver la venta cancelada");
        assertFalse(ventaCancelada.getEstado(), "El estado de la venta debería estar en false (cancelado)");
    }
    
    @Test
    public void testCancelarVentaCancelada() throws PersistenciaException {
        Venta ventaACancelar = new Venta("FOLIOCANCELAR", LocalDateTime.now(), 300.0,
                new ObjectId("681c7271618fd87bdcee6ad1"), List.of());
        ventaDAO.agregar(ventaACancelar);
        ventaDAO.cancelar(ventaACancelar.getId().toHexString());
        
        assertThrows(PersistenciaException.class, () -> {
            ventaDAO.cancelar(ventaACancelar.getId().toHexString());
        });
    }
    
    @Test
    public void testObtenerPorFolioExistente() throws PersistenciaException {
        Venta ventaConFolio = new Venta("FOLIOUNICO", LocalDateTime.now(), 150.0,
                new ObjectId("681c7271618fd87bdcee6ad1"), List.of());
        ventaDAO.agregar(ventaConFolio);

        Venta ventaObtenida = ventaDAO.obtenerPorFolio("FOLIOUNICO");
        assertNotNull(ventaObtenida, "Debería obtener una venta con el folio proporcionado");
        assertEquals("FOLIOUNICO", ventaObtenida.getFolio(), "Los folios deberían coincidir");
    }
    
    @Test
    public void testObtenerPorFolioInexistente() throws PersistenciaException {
        Venta ventaObtenida = ventaDAO.obtenerPorFolio("FOLIOQUE_NO_EXISTE");
        assertNull(ventaObtenida, "No debería encontrar una venta con un folio inexistente");
    }
    
    @Test
    public void testObtenerDetallesDeVentaExistente() throws PersistenciaException {
        Venta ventaConDetalles = new Venta("FOLIODETALLE", LocalDateTime.now(), 250.0,
                new ObjectId("681c7271618fd87bdcee6ad1"), List.of(
                        new DetalleVenta(new ObjectId("681bd995c1b020138e159a02"), 2, 50.0, 100.0),
                        new DetalleVenta(new ObjectId("681bd995c1b020138e159a02"), 1, 150.0, 100.0)
                ));
        ventaDAO.agregar(ventaConDetalles);

        List<DetalleVenta> detalles = ventaDAO.obtenerDetalles(ventaConDetalles.getId().toHexString());
        assertNotNull(detalles, "La lista de detalles no debería ser nula");
        assertEquals(2, detalles.size(), "Debería tener 2 detalles registrados");
    }
    
    @Test
    public void testObtenerDetallesDeVentaInexistente() throws PersistenciaException{
        assertThrows(PersistenciaException.class, () -> {
            ventaDAO.obtenerDetalles(new ObjectId().toHexString());
        });
    }
    @Test
    public void testConsultarTodos() throws PersistenciaException {
        List<Venta> ventas = ventaDAO.obtenerTodas();
        assertFalse(ventas.isEmpty(), "Debería haber al menos una venta en la base de datos");
    }
    
    @Test
    public void testObtenerPorIdExistente() throws PersistenciaException {
        Venta ventaObtenida = ventaDAO.obtenerPorId(ventaValida.getId().toHexString());
        assertNotNull(ventaObtenida, "Debería obtener una venta con el ID proporcionado");
        assertEquals(ventaValida.getId(), ventaObtenida.getId(), "Los IDs deberían coincidir");
    }
    
    @Test
    public void testObtenerPorIdInvalido() throws PersistenciaException {
        assertThrows(PersistenciaException.class, () -> {
            ventaDAO.obtenerPorId("pensativo");
        });
    }

    @Test
    public void testObtenerPorIdInexistente() throws PersistenciaException {
        Venta ventaObtenida = ventaDAO.obtenerPorId(new ObjectId().toHexString());
        assertNull(ventaObtenida, "No debería encontrar una venta con un ID inexistente");
    }

    @Test
    public void testObtenerPorFiltrosSinFiltro() throws PersistenciaException {
        List<Venta> ventas = ventaDAO.obtenerPorFiltros(null, null, null, null, null);
        assertFalse(ventas.isEmpty(), "Debería devolver ventas al no aplicar filtros");
    }

    @Test
    public void testObtenerPorFiltrosConFolio() throws PersistenciaException {
        List<Venta> ventas = ventaDAO.obtenerPorFiltros(null, null, ventaValida.getFolio(), null, null);
        assertFalse(ventas.isEmpty(), "Debería encontrar ventas con ese proveedor");
    }

    @Test
    public void testObtenerPorFiltrosConFechaInicio() throws PersistenciaException {
        LocalDateTime fechaInicio = LocalDateTime.now().minusDays(1);
        List<Venta> ventas = ventaDAO.obtenerPorFiltros(fechaInicio, null, null, null, null);
        assertFalse(ventas.isEmpty(), "Debería encontrar ventas desde la fecha de inicio");
    }

    @Test
    public void testObtenerPorFiltrosConFechaFin() throws PersistenciaException {
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(1);
        List<Venta> ventas = ventaDAO.obtenerPorFiltros(null, fechaFin, null, null, null);
        assertFalse(ventas.isEmpty(), "Debería encontrar ventas hasta la fecha de fin");
    }

    @Test
    public void testObtenerPorFiltrosConNombreProducto() throws PersistenciaException {
        List<Venta> ventas = ventaDAO.obtenerPorFiltros(null, null, null, "a", null);
        assertFalse(ventas.isEmpty(), "Debería encontrar ventas con ese nombre de producto");
    }

    @Test
    public void testObtenerPorFiltrosConRangoDeFechas() throws PersistenciaException {
        LocalDateTime fechaInicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(1);
        List<Venta> ventas = ventaDAO.obtenerPorFiltros(fechaInicio, fechaFin, null, null, null);
        assertFalse(ventas.isEmpty(), "Debería encontrar ventas en el rango de fechas");
    }

    @Test
    public void testObtenerPorFiltrosConEstado() throws PersistenciaException {
        List<Venta> ventas = ventaDAO.obtenerPorFiltros(null, null, null, null, true);
        assertFalse(ventas.isEmpty(), "Debería encontrar ventas con estado true");
    }
}
