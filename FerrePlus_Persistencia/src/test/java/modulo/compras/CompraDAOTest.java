/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package modulo.compras;

import entidades.Compra;
import entidades.DetalleCompra;
import excepciones.PersistenciaException;
import java.time.LocalDate;
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
public class CompraDAOTest {
    private ICompraDAO compraDAO;
    private Compra compraValida;
    private ObjectId productoIdPrueba = new ObjectId("681bd995c1b020138e159a02");

    @BeforeEach
    public void setUp() throws PersistenciaException {
        compraDAO = CompraDAO.getInstanceDAO();
        
        List<DetalleCompra> detalles = Arrays.asList(
            new DetalleCompra(productoIdPrueba, 2, 15.0, 30.0)
        );

        compraValida = new Compra("FOLIO-TEST", LocalDate.now(), 30.0, "Proveedor Test", detalles);
        compraValida = compraDAO.agregar(compraValida);
    }

    @AfterEach
    public void tearDown() throws PersistenciaException {
        //Por el momento cero que no es necesario
    }

    @Test
    public void testAgregarCompraCorrectamente() throws PersistenciaException {
        List<DetalleCompra> detalles = Arrays.asList(
            new DetalleCompra(productoIdPrueba, 1, 20.0, 20.0)
        );

        Compra nuevaCompra = new Compra("A001", LocalDate.now(), 20.0, "PURO OBSON PRRO", detalles);
        Compra compraInsertada = compraDAO.agregar(nuevaCompra);

        assertNotNull(compraInsertada.getId());
        assertEquals("A001", compraInsertada.getFolio());
    }

    @Test
    public void testAgregarCompraNula() {
        assertThrows(PersistenciaException.class, () -> {
            compraDAO.agregar(null);
        });
    }

    @Test
    public void testObtenerPorIdExistente() throws PersistenciaException {
        Compra compraObtenida = compraDAO.obtenerPorId(compraValida.getId().toHexString());
        assertNotNull(compraObtenida);
        assertEquals(compraValida.getFolio(), compraObtenida.getFolio());
    }

    @Test
    public void testObtenerPorIdNoExistente() {
        assertThrows(PersistenciaException.class, () -> {
            compraDAO.obtenerPorId("id-invalido");
        });
    }

    @Test
    public void testObtenerPorFolioExistente() throws PersistenciaException {
        Compra compraObtenida = compraDAO.obtenerPorFolio(compraValida.getFolio());
        assertNotNull(compraObtenida);
        assertEquals(compraValida.getFolio(), compraObtenida.getFolio());
    }

    @Test
    public void testObtenerPorFolioNulo() {
        assertThrows(PersistenciaException.class, () -> {
            compraDAO.obtenerPorFolio(null);
        });
    }

    @Test
    public void testObtenerTodas() throws PersistenciaException {
        List<Compra> compras = compraDAO.obtenerTodas();
        assertFalse(compras.isEmpty());
    }

    @Test
    public void testObtenerDetallesCorrecto() throws PersistenciaException {
        List<DetalleCompra> detalles = compraDAO.obtenerDetalles(compraValida.getId().toHexString());
        assertNotNull(detalles);
        assertFalse(detalles.isEmpty());
    }

    @Test
    public void testObtenerDetallesConIdInvalido() {
        assertThrows(PersistenciaException.class, () -> {
            compraDAO.obtenerDetalles("id-invalido");
        });
    }

    @Test
    public void testObtenerPorFiltrosSinFiltro() throws PersistenciaException {
        List<Compra> compras = compraDAO.obtenerPorFiltros(null, null, null, null);
        assertFalse(compras.isEmpty());
    }
    
    @Test
    public void testObtenerPorFiltrosConProveedor() throws PersistenciaException {
        List<Compra> compras = compraDAO.obtenerPorFiltros(null, null, compraValida.getProveedor(), null);
        assertFalse(compras.isEmpty());
    }

    @Test
    public void testObtenerPorFiltrosConFechaInicio() throws PersistenciaException {
        LocalDate fechaInicio = LocalDate.now().minusDays(1);
        List<Compra> compras = compraDAO.obtenerPorFiltros(fechaInicio, null, null, null);
        assertFalse(compras.isEmpty());
    }

    @Test
    public void testObtenerPorFiltrosConFechaFin() throws PersistenciaException {
        LocalDate fechaFin = LocalDate.now().plusDays(1);
        List<Compra> compras = compraDAO.obtenerPorFiltros(null, fechaFin, null, null);
        assertFalse(compras.isEmpty());
    }

    @Test
    public void testObtenerPorFiltrosConNombre() throws PersistenciaException {
        List<Compra> compras = compraDAO.obtenerPorFiltros(null, null, null, "a");
        assertFalse(compras.isEmpty());
    }

    @Test
    public void testObtenerPorFiltrosConRangoDeFechas() throws PersistenciaException {
        LocalDate fechaInicio = LocalDate.now().minusDays(1);
        LocalDate fechaFin = LocalDate.now().plusDays(1);
        List<Compra> compras = compraDAO.obtenerPorFiltros(fechaInicio, fechaFin, null, null);
        assertFalse(compras.isEmpty());
    }
}
