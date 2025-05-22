/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DTO.CajaDTO;
import Interfaces.ICajaBO;
import entidades.Caja;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.CajaMapper;
import modulo.caja.ICajaDAO;
import modulo.inventario.IProductoDAO;

/**
 *
 * @author Beto_
 */
public class CajaBO implements ICajaBO{
    private ICajaDAO cajaDAO;

    public CajaBO() {
        try {
            this.cajaDAO = modulo.caja.CajaDAO.getInstanceDAO();
        } catch (PersistenciaException ex) {
            Logger.getLogger(CajaBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public CajaDTO abrir(CajaDTO cajaDTO) throws NegocioException {
        // 1. Validaciones de Negocio (basadas en tus reglas)
        if (cajaDTO == null) {
            throw new NegocioException("La información de la caja no puede ser nula para la apertura.");
        }
        if (cajaDTO.getMontoInicial() == null || cajaDTO.getMontoInicial() < 0) { // Regla: Monto inicial positivo o cero
            throw new NegocioException("El monto inicial debe ser un valor positivo o cero.");
        }
        
        try {
            CajaDTO sesionActivaExistente = obtenerSesionActiva();
            if (sesionActivaExistente != null && sesionActivaExistente.getEstadoSesion()) {
                throw new NegocioException("Ya existe una sesión de caja activa. Cierre la sesión anterior antes de abrir una nueva. Fecha: " + sesionActivaExistente.getFechaHoraApertura());
            }
        } catch (NegocioException e) {
            throw new NegocioException("Error al verificar sesiones activas existentes: " + e.getMessage(), e);
        }

        // 2. Convertir DTO a Entidad utilizando el Mapper (para apertura)
        // La fecha y hora de apertura, estadoSesion (true), y los contadores se inicializan en el constructor de Caja
        Caja cajaEntity = CajaMapper.toEntityForOpening(cajaDTO);

        // 3. Llamar a la capa de persistencia
        try {
            System.out.println("Negocio - Vamos a guardar la caja: " + cajaEntity.toString());
            Caja cajaGuardada = cajaDAO.abrir(cajaEntity);
            // 4. Convertir Entidad a DTO y retornar utilizando el Mapper
            return CajaMapper.toDTO(cajaGuardada);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar la apertura de caja: " + e.getMessage(), e);
        }
    }

    @Override
    public CajaDTO cerrar(CajaDTO cajaDTO) throws NegocioException {
        // 1. Validaciones de Negocio (basadas en tus reglas)
        if (cajaDTO == null) {
            throw new NegocioException("La información de la caja no puede ser nula para el cierre.");
        }
        if (cajaDTO.getId() == null || cajaDTO.getId().trim().isEmpty()) {
            throw new NegocioException("El ID de la caja es obligatorio para el cierre.");
        }
        // Fecha y hora de cierre (automática), totalVentas, cantidadProductos, numeroVentas (automáticos)
        // Monto final estimado (automático) - estos se calcularán o recuperarán del DAO antes de pasar a cerrar.

        // Regla de Negocio: La caja a cerrar debe ser la sesión activa actual y no debe estar ya cerrada.
        CajaDTO sesionActiva = null;
        try {
            sesionActiva = obtenerSesionActiva(); // Obtener la sesión activa del DAO
        } catch (NegocioException e) {
            throw new NegocioException("Error al verificar la sesión activa para el cierre: " + e.getMessage(), e);
        }

        if (sesionActiva == null) {
            throw new NegocioException("No hay ninguna sesión de caja activa para cerrar.");
        }
        if (!sesionActiva.getId().equals(cajaDTO.getId())) {
            throw new NegocioException("La caja con ID " + cajaDTO.getId() + " no es la sesión activa actual. Solo se puede cerrar la sesión activa.");
        }
        if (!sesionActiva.getEstadoSesion()) { // Redundante si ya verificamos que es la activa, pero es una buena doble verificación
            throw new NegocioException("La sesión de caja con ID " + cajaDTO.getId() + " ya está cerrada.");
        }

        // Regla de Negocio: Calcular campos automáticos de cierre
        // Usar los valores actuales de la sesión activa
        cajaDTO.setFechaHoraCierre(LocalDateTime.now()); // Automática
        cajaDTO.setTotalVentas(sesionActiva.getTotalVentas()); // Automática
        cajaDTO.setCantidadDeProductos(sesionActiva.getCantidadDeProductos()); // Automática
        cajaDTO.setNumeroDeVentas(sesionActiva.getNumeroDeVentas()); // Automática
        
        // Monto final estimado = monto inicial + total de ventas
        cajaDTO.setMontoFinalEstimado(sesionActiva.getMontoInicial() + sesionActiva.getTotalVentas()); // Automática

        // 2. Convertir DTO a Entidad utilizando el Mapper
        Caja cajaEntity = CajaMapper.toEntity(cajaDTO);

        // 3. Llamar a la capa de persistencia
        try {
            Caja cajaCerrada = cajaDAO.cerrar(cajaEntity); // Tu DAO cierra la caja (actualiza campos y estado)
            // La regla dice "Una vez cerrada, la sesión ya no podrá modificarse."
            // "Se bloqueará el registro de nuevas ventas hasta abrir una nueva caja."
            // Estos son resultados de la acción de cerrar, no validaciones previas.
            return CajaMapper.toDTO(cajaCerrada); // El DAO debería devolver la entidad actualizada
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar el cierre de caja: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizarResumenVentas(String id, double totalVentasInc, int cantidadProductosInc, int numeroVentasInc) throws NegocioException {
        // 1. Validaciones de Negocio
        if (id == null || id.trim().isEmpty()) {
            throw new NegocioException("El ID de la caja es obligatorio para actualizar el resumen de ventas.");
        }

        // Regla de Negocio: No se permitirá registrar ninguna venta si no existe una sesión de caja abierta.
        // Implica que solo se puede actualizar el resumen de una sesión activa.
        try {
            CajaDTO cajaExistente = obtenerPorId(id); // Obtener el estado actual de la caja
            if (cajaExistente == null) {
                throw new NegocioException("No se encontró la caja con ID: " + id + " para actualizar su resumen de ventas.");
            }
            if (!cajaExistente.getEstadoSesion()) {
                throw new NegocioException("No se puede actualizar el resumen de ventas de una caja que no está activa.");
            }

        } catch (NegocioException e) {
            throw new NegocioException("Error al verificar la caja para actualizar resumen de ventas: " + e.getMessage(), e);
        }

        // 2. Llamar a la capa de persistencia
        try {
            return cajaDAO.actualizarResumenVentas(id, totalVentasInc, cantidadProductosInc, numeroVentasInc);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar el resumen de ventas: " + e.getMessage(), e);
        }
    }

    @Override
    public CajaDTO obtenerPorId(String id) throws NegocioException {
        // 1. Validaciones de Negocio
        if (id == null || id.trim().isEmpty()) {
            throw new NegocioException("El ID de la caja es obligatorio para la consulta.");
        }

        // 2. Llamar a la capa de persistencia
        try {
            Caja cajaEntity = cajaDAO.obtenerPorId(id);
            // 3. Convertir Entidad a DTO y retornar utilizando el Mapper
            return CajaMapper.toDTO(cajaEntity); // toDTO ya maneja el caso de entidad nula
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener la caja por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public CajaDTO obtenerSesionActiva() throws NegocioException {
        // 1. Llamar a la capa de persistencia
        try {
            Caja cajaEntity = cajaDAO.obtenerSesionActiva();
            // 3. Convertir Entidad a DTO y retornar utilizando el Mapper
            return CajaMapper.toDTO(cajaEntity); // toDTO ya maneja el caso de entidad nula
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener la sesión de caja activa: " + e.getMessage(), e);
        }
    }
}
