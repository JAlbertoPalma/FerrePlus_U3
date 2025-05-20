/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package modulo.caja;

import entidades.Caja;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
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
public class CajaDAOTest {
    private ICajaDAO cajaDAO;
    private Caja caja;

    @BeforeEach
    void setUp() throws PersistenciaException {
        // Instanciar DAO y crear una caja base
        cajaDAO = CajaDAO.getInstanceDAO();
        caja = new Caja(5000.0);
    }

    @AfterEach
    void tearDown() throws PersistenciaException {
        // Si hay una caja activa al terminar, la cerramos
        Caja activa = cajaDAO.obtenerSesionActiva();
        if (activa != null) {
            activa.setMontoFinalEstimado(activa.getMontoInicial() + activa.getTotalVentas());
            activa.setFechaHoraCierre(LocalDateTime.now());
            activa.setObservacionesCierre("Cierre automático de prueba");
            cajaDAO.cerrar(activa);
        }
    }

    // PRUEBAS POSITIVAS

    @Test
    void testAbrirCajaCorrectamente() throws PersistenciaException {
        Caja result = cajaDAO.abrir(caja);
        assertNotNull(result.getId());
        assertEquals(5000.0, result.getMontoInicial());
        assertTrue(result.getEstadoSesion());
    }

    @Test
    void testObtenerSesionActivaCorrectamente() throws PersistenciaException {
        cajaDAO.abrir(caja);
        Caja activa = cajaDAO.obtenerSesionActiva();
        assertNotNull(activa);
        assertEquals(caja.getMontoInicial(), activa.getMontoInicial());
        assertTrue(activa.getEstadoSesion());
    }

    @Test
    void testActualizarResumenVentasCorrectamente() throws PersistenciaException {
        cajaDAO.abrir(caja);
        boolean resultado = cajaDAO.actualizarResumenVentas(caja.getId().toHexString(), 100, 5, 2);
        assertTrue(resultado);

        Caja actualizada = cajaDAO.obtenerPorId(caja.getId().toHexString());
        assertEquals(100.0, actualizada.getTotalVentas());
        assertEquals(5, actualizada.getCantidadDeProductos());
        assertEquals(2, actualizada.getNumeroDeVentas());
    }

    @Test
    void testObtenerCajaPorIdCorrectamente() throws PersistenciaException {
        cajaDAO.abrir(caja);
        Caja encontrada = cajaDAO.obtenerPorId(caja.getId().toHexString());
        assertNotNull(encontrada);
        assertEquals(caja.getMontoInicial(), encontrada.getMontoInicial());
    }

    @Test
    void testCerrarCajaCorrectamente() throws PersistenciaException {
        cajaDAO.abrir(caja);
        caja.setMontoFinalEstimado(caja.getMontoInicial() + 100);
        caja.setFechaHoraCierre(LocalDateTime.now());
        caja.setObservacionesCierre("Cierre de prueba");

        Caja cerrada = cajaDAO.cerrar(caja);
        assertFalse(cerrada.getEstadoSesion());
        assertNotNull(cerrada.getFechaHoraCierre());
        assertEquals(caja.getMontoFinalEstimado(), cerrada.getMontoFinalEstimado());
        assertEquals("Cierre de prueba", cerrada.getObservacionesCierre());
    }

    @Test
    void testObtenerSesionActivaDevuelveNullTrasCierre() throws PersistenciaException {
        cajaDAO.abrir(caja);
        caja.setMontoFinalEstimado(caja.getMontoInicial());
        caja.setFechaHoraCierre(LocalDateTime.now());
        caja.setObservacionesCierre("Cierre normal");
        cajaDAO.cerrar(caja);

        Caja activa = cajaDAO.obtenerSesionActiva();
        assertNull(activa);
    }
    
    // PRUEBAS NEGATIVAS

    @Test
    void testAbrirCajaNula() {
        assertThrows(PersistenciaException.class, () -> {
            cajaDAO.abrir(null);
        });
    }

    @Test
    void testCerrarCajaSinCajaActiva() throws PersistenciaException {
        // Cerrar la única caja activa
        cajaDAO.abrir(caja);
        caja.setMontoFinalEstimado(5000.0);
        caja.setFechaHoraCierre(LocalDateTime.now());
        caja.setObservacionesCierre("Cierre normal");
        cajaDAO.cerrar(caja);

        // Intentar cerrar otra vez
        PersistenciaException exception = assertThrows(PersistenciaException.class, () -> {
            cajaDAO.cerrar(caja);
        });
        assertTrue(exception.getMessage().contains("No hay cajas que cerrar"));
    }

    @Test
    void testCerrarCajaNula() {
        assertThrows(PersistenciaException.class, () -> {
            cajaDAO.cerrar(null);
        });
    }

    @Test
    void testCerrarCajaYaCerrada() throws PersistenciaException {
        // Cerramos la caja primero
        cajaDAO.abrir(caja);
        caja.setMontoFinalEstimado(5000.0);
        caja.setFechaHoraCierre(LocalDateTime.now());
        caja.setObservacionesCierre("Cierre normal");
        cajaDAO.cerrar(caja);

        // Intentar cerrarla otra vez (ya está cerrada)
        PersistenciaException exception = assertThrows(PersistenciaException.class, () -> {
            cajaDAO.cerrar(caja);
        });
        assertTrue(exception.getMessage().contains("No hay cajas que cerrar"));
    }

    @Test
    void testActualizarResumenVentasCajaInexistente() {
        assertThrows(PersistenciaException.class, () -> {
            cajaDAO.actualizarResumenVentas("507f1f77bcf86cd799439011", 100, 2, 1);
        });
    }

    @Test
    void testObtenerCajaPorIdNulo() {
        assertThrows(PersistenciaException.class, () -> {
            cajaDAO.obtenerPorId(null);
        });
    }

    @Test
    void testObtenerCajaPorIdInvalido() {
        PersistenciaException exception = assertThrows(PersistenciaException.class, () -> {
            cajaDAO.obtenerPorId("id-no-valido");
        });
        assertTrue(exception.getMessage().contains("Id inválido"));
    }

    @Test
    void testObtenerCajaPorIdNoExistente() throws PersistenciaException {
        Caja resultado = cajaDAO.obtenerPorId("507f1f77bcf86cd799439011");
        assertNull(resultado);
    }

    @Test
    void testObtenerSesionActivaSinCaja() throws PersistenciaException {
        // Cerramos caja activa
        cajaDAO.abrir(caja);
        caja.setMontoFinalEstimado(5000.0);
        caja.setFechaHoraCierre(LocalDateTime.now());
        caja.setObservacionesCierre("Cierre normal");
        cajaDAO.cerrar(caja);

        // Debe regresar null
        Caja activa = cajaDAO.obtenerSesionActiva();
        assertNull(activa);
    }    
    
}
