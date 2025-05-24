/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DTO.DetalleVentaDTO;
import DTO.VentaDTO;
import Interfaces.IVentaBO;
import entidades.DetalleVenta;
import entidades.Producto;
import entidades.Venta;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mappers.DetalleVentaMapper;
import mappers.VentaMapper;
import modulo.inventario.IProductoDAO;
import modulo.ventas.IVentaDAO;

/**
 *
 * @author Beto_
 */
public class VentaBO implements IVentaBO {

    private IVentaDAO ventaDAO;
    private IProductoDAO productoDAO;
    private static AtomicInteger folioCounter = new AtomicInteger(0);
    private static final Logger logger = Logger.getLogger(VentaBO.class.getName());

    public VentaBO() {
        try {
            this.productoDAO = modulo.inventario.ProductoDAO.getInstanceDAO();
            this.ventaDAO = modulo.ventas.VentaDAO.getInstanceDAO();
        } catch (PersistenciaException ex) {
            Logger.getLogger(CompraBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Genera un folio único para la venta con el formato "VT-YYYYMMDD-XXX".
     *
     * @return El folio generado.
     */
    private String generarFolio() {
        LocalDateTime now = LocalDateTime.now(); // Usamos LocalDateTime para la venta
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String fechaParte = now.format(formatter);
        int numeroConsecutivo = folioCounter.incrementAndGet();
        return String.format("VT-%s-%03d", fechaParte, numeroConsecutivo);
    }

    @Override
    public VentaDTO agregar(VentaDTO ventaDTO) throws NegocioException {
        // --- 1. Validaciones de Negocio Pre-persistencias ---
        if (ventaDTO == null) {
            JOptionPane.showMessageDialog(null, "La información de la venta no puede ser nula.");
            throw new NegocioException("La información de la venta no puede ser nula.");
        }
        // Establecer la fecha y hora actual si no se proporciona en el DTO
        if (ventaDTO.getFechaHora() == null) {
            ventaDTO.setFechaHora(LocalDateTime.now());
        }

        System.out.println("VentaBO : detalles" + ventaDTO.getDetalles());
        if (ventaDTO.getDetalles() == null || ventaDTO.getDetalles().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Una venta debe tener al menos un producto en sus detalles.");
            throw new NegocioException("Una venta debe tener al menos un producto en sus detalles.");
        }
        if (ventaDTO.getIdCaja() == null || ventaDTO.getIdCaja().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El ID de la caja es obligatorio para la venta.");
            throw new NegocioException("El ID de la caja es obligatorio para la venta.");
        }

        double totalCalculado = 0.0;

        // Iterar sobre los detalles de la venta para validaciones y cálculo del total
        for (DetalleVentaDTO detalle : ventaDTO.getDetalles()) {
            if (detalle.getIdProducto() == null || detalle.getIdProducto().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El ID del producto en el detalle no puede ser nulo o vacío.");
                throw new NegocioException("El ID del producto en el detalle no puede ser nulo o vacío.");
            }
            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
                JOptionPane.showMessageDialog(null, "La cantidad vendida para un producto debe ser positiva.");
                throw new NegocioException("La cantidad vendida para un producto debe ser positiva.");
            }
            if (detalle.getDescuento() == null || detalle.getDescuento() < 0) {
                JOptionPane.showMessageDialog(null, "El descuento no puede ser negativo.");
                throw new NegocioException("El descuento no puede ser negativo.");
            }
            // Aunque el subtotal se recalcule, se mantiene una validación inicial de no negativo.
            if (detalle.getSubtotal() == null || detalle.getSubtotal() < 0) {
                JOptionPane.showMessageDialog(null, "El subtotal de un producto en el detalle no puede ser negativo.");
                throw new NegocioException("El subtotal de un producto en el detalle no puede ser negativo.");
            }

            // Regla: Validar que cada producto esté registrado en el inventario y obtener sus datos.
            Producto productoExistente;
            try {
                productoExistente = productoDAO.obtenerPorId(detalle.getIdProducto());
            } catch (PersistenciaException e) {
                logger.log(Level.SEVERE, "Error de persistencia al verificar existencia del producto ID: " + detalle.getIdProducto(), e);
                JOptionPane.showMessageDialog(null, "Error al verificar la existencia del producto con ID.");
                throw new NegocioException("Error al verificar la existencia del producto con ID " + detalle.getIdProducto() + ": " + e.getMessage(), e);
            }
            if (productoExistente == null) {
                JOptionPane.showMessageDialog(null, "El producto con ID " + detalle.getIdProducto() + " no está registrado en el inventario.");
                throw new NegocioException("El producto con ID " + detalle.getIdProducto() + " no está registrado en el inventario.");
            }

            // Regla: Verificar stock disponible del producto.
            if (productoExistente.getStock() == null || productoExistente.getStock() < detalle.getCantidad()) {
                JOptionPane.showMessageDialog(null, "Stock insuficiente para el producto '" + productoExistente.getNombre() + "'. Stock disponible: " + productoExistente.getStock() + ", cantidad solicitada: " + detalle.getCantidad());
                throw new NegocioException("Stock insuficiente para el producto '" + productoExistente.getNombre() + "'. Stock disponible: " + productoExistente.getStock() + ", cantidad solicitada: " + detalle.getCantidad());
            }

            // Regla: Recalcular el subtotal para asegurar consistencia (precio de venta * cantidad * (1 - descuento %)).
            double precioVentaUnitario = productoExistente.getPrecioVenta(); // Asumiendo que `Producto` tiene un `getPrecioVenta()`
            double subtotalCalculado = (detalle.getCantidad() * precioVentaUnitario) * (1 - (detalle.getDescuento() / 100.0));
            detalle.setSubtotal(subtotalCalculado); // Actualiza el subtotal en el DTO

            totalCalculado += subtotalCalculado;
        }
        ventaDTO.setFechaHora(LocalDateTime.now());

        // Regla: Calcular y establecer el total de la venta.
        ventaDTO.setTotal(totalCalculado);

        // Regla: Establecer el estado de la venta como activa por defecto al ser creada.
        ventaDTO.setEstado(true);

        try {
            // Regla: Generar un folio único con el formato CP-YYYYMMDD-XXX
            ventaDTO.setFolio(ventaDAO.obtenerSiguienteFolio());
        } catch (PersistenciaException pe) {
            JOptionPane.showMessageDialog(null, "Error al obtener el folio.");
            throw new NegocioException("Error al obtener el folio: " + pe.getMessage(), pe);
        }

        // --- 3. Mapeo DTO a Entidad ---
        Venta ventaEntity;
        try {
            // El VentaMapper mapea el DTO a la entidad. Si hay IDs inválidos en el DTO (propio o en detalles),
            // el mapper lanzará una NegocioException.
            ventaEntity = VentaMapper.toEntity(ventaDTO);
        } catch (NegocioException e) { // Captura la NegocioException lanzada por el mapper
            logger.log(Level.SEVERE, "Error de mapeo (VentaDTO a Entidad) en VentaBO.agregar: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Error de mapeo (VentaDTO a Entidad) en VentaBO.agregar");
            throw e; // Relanza la misma excepción de negocio
        } catch (Exception e) { // Para cualquier otra excepción inesperada durante el mapeo
            logger.log(Level.SEVERE, "Error inesperado al mapear la venta a entidad en VentaBO.agregar: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Error inesperado al mapear la venta a entidad en VentaBO.agregar");
            throw new NegocioException("Error inesperado al mapear la venta a entidad: " + e.getMessage(), e);
        }

        // --- 4. Persistencia de la Venta ---
        Venta ventaGuardada;
        try {
            ventaGuardada = ventaDAO.agregar(ventaEntity);
        } catch (PersistenciaException e) {
            // Manejar específicamente el error de clave duplicada si el folio ya existe (por índice único)
            if (e.getMessage() != null && e.getMessage().contains("E11000 duplicate key error") || e.getMessage().contains("duplicate key")) {
                JOptionPane.showMessageDialog(null, "Error al registrar la venta: ya existe una venta con el folio '" + ventaDTO.getFolio() + "'. Por favor, intente de nuevo.");
                throw new NegocioException("Error al registrar la venta: ya existe una venta con el folio '" + ventaDTO.getFolio() + "'. Por favor, intente de nuevo.", e);
            }
            logger.log(Level.SEVERE, "Error al agregar la venta en la base de datos: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Error al agregar la venta en la base de datos.");
            throw new NegocioException("Error al agregar la venta en la base de datos: " + e.getMessage(), e);
        }

        // --- 5. Actualización de Stock de Productos ---
        // Se realiza después de que la venta se haya guardado exitosamente para asegurar la consistencia.
        try {
            // Iterar sobre los detalles de la venta que ya fue guardada (asegurando IDs de producto válidos)
            for (DetalleVenta detalle : ventaGuardada.getDetalles()) {
                Producto producto = productoDAO.obtenerPorId(detalle.getIdProducto().toHexString());
                if (producto == null) {
                    logger.log(Level.WARNING, "Producto con ID " + detalle.getIdProducto().toHexString() + " no encontrado en el inventario al actualizar stock para la venta " + ventaGuardada.getFolio() + ". Reversión de stock incompleta.");
                    // Esto indica una inconsistencia severa si el producto no se encontró después de la venta.
                } else {
                    producto.setStock(producto.getStock() - detalle.getCantidad()); // Decrementar el stock
                    productoDAO.actualizar(producto); // Persistir el cambio en el stock
                }
            }
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "La venta se registró con folio " + ventaGuardada.getFolio() + ", pero hubo un error crítico al actualizar el inventario: " + e.getMessage(), e);
            // Para un sistema robusto, esta situación debería activar un mecanismo de compensación (rollback)
            // o un sistema de reintento/alerta para asegurar la consistencia del inventario.
            JOptionPane.showMessageDialog(null, "La venta se registró, pero hubo un error crítico al actualizar el inventario. Se requiere revisión manual y posible ajuste de stock.");
            throw new NegocioException("La venta se registró, pero hubo un error crítico al actualizar el inventario. Se requiere revisión manual y posible ajuste de stock.", e);
        }

        // --- 6. Mapear Entidad guardada a DTO y retornar ---
        return VentaMapper.toDTO(ventaGuardada);
    }

    @Override
    public VentaDTO cancelar(String id) throws NegocioException {
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El ID de la venta no puede ser nulo o vacío para cancelar.");
            throw new NegocioException("El ID de la venta no puede ser nulo o vacío para cancelar.");
        }
        try {
            Venta ventaExistente = ventaDAO.obtenerPorId(id);
            if (ventaExistente == null) {
                JOptionPane.showMessageDialog(null, "No se encontró la venta con ID: " + id + " para cancelar.");
                throw new NegocioException("No se encontró la venta con ID: " + id + " para cancelar.");
            }
            if (!ventaExistente.getEstado()) { // Si el estado es false, ya está cancelada
                JOptionPane.showMessageDialog(null, "La venta con folio " + ventaExistente.getFolio() + " ya se encuentra cancelada y no puede ser cancelada nuevamente.");
                throw new NegocioException("La venta con folio " + ventaExistente.getFolio() + " ya se encuentra cancelada y no puede ser cancelada nuevamente.");
            }

            ventaExistente.setEstado(false); // Marcar la venta como cancelada

            // Revertir stock de productos: aumentar el stock de cada producto en el inventario.
            for (DetalleVenta detalle : ventaExistente.getDetalles()) {
                Producto producto = productoDAO.obtenerPorId(detalle.getIdProducto().toHexString());
                if (producto == null) {
                    logger.log(Level.WARNING, "Producto con ID " + detalle.getIdProducto().toHexString() + " no encontrado al cancelar la venta " + ventaExistente.getFolio() + ". La reversión de stock para este producto es incompleta.");
                } else {
                    producto.setStock(producto.getStock() + detalle.getCantidad()); // Aumentar el stock
                    productoDAO.actualizar(producto); // Persistir el cambio
                }
            }

            // Actualizar la venta en la base de datos con el nuevo estado
            Venta ventaActualizada = ventaDAO.cancelar(id);
            return VentaMapper.toDTO(ventaActualizada);

        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al cancelar la venta con ID " + id + ": " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Error al cancelar la venta con ID.");
            throw new NegocioException("Error al cancelar la venta con ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public VentaDTO obtenerPorId(String id) throws NegocioException {
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El ID de la venta no puede ser nulo o vacío para la consulta.");
            throw new NegocioException("El ID de la venta no puede ser nulo o vacío para la consulta.");
        }
        try {
            Venta ventaEntity = ventaDAO.obtenerPorId(id);
            return VentaMapper.toDTO(ventaEntity); // Mapea la entidad a DTO para retornar
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al obtener la venta por ID " + id + ": " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Error al obtener la venta por ID.");
            throw new NegocioException("Error al obtener la venta por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public VentaDTO obtenerPorFolio(String folio) throws NegocioException {
        if (folio == null || folio.trim().isEmpty()) {
               JOptionPane.showMessageDialog(null, "El folio de la venta no puede ser nulo o vacío para la consulta.");
            throw new NegocioException("El folio de la venta no puede ser nulo o vacío para la consulta.");
        }
        try {
            Venta ventaEntity = ventaDAO.obtenerPorFolio(folio);
            return VentaMapper.toDTO(ventaEntity); // Mapea la entidad a DTO para retornar
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al obtener la venta por folio " + folio + ": " + e.getMessage(), e);
             JOptionPane.showMessageDialog(null, "Error al obtener la venta por folio.");
            throw new NegocioException("Error al obtener la venta por folio: " + e.getMessage(), e);
        }
    }

    @Override
    public List<VentaDTO> obtenerTodas() throws NegocioException {
        try {
            List<Venta> ventas = ventaDAO.obtenerTodas();
            return VentaMapper.toDTOList(ventas); // Mapea la lista de entidades a lista de DTOs
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al obtener todas las ventas: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Error al obtener todas las ventas.");
            throw new NegocioException("Error al obtener todas las ventas: " + e.getMessage(), e);
        }
    }

    @Override
    public List<VentaDTO> obtenerPorFiltros(LocalDateTime fechaInicio, LocalDateTime fechaFin, String folio, String nombreProducto, Boolean estado) throws NegocioException {
        try {
            List<Venta> ventasFiltradas = ventaDAO.obtenerPorFiltros(fechaInicio, fechaFin, folio, nombreProducto, estado);
            return VentaMapper.toDTOList(ventasFiltradas); // Mapea la lista de entidades a lista de DTOs
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al obtener ventas por filtros: " + e.getMessage(), e);
                 JOptionPane.showMessageDialog(null, "Error al obtener ventas por filtros.");
            throw new NegocioException("Error al obtener ventas por filtros: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DetalleVentaDTO> obtenerDetalles(String id) throws NegocioException {
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El ID de la venta no puede ser nulo o vacío para obtener sus detalles.");
            throw new NegocioException("El ID de la venta no puede ser nulo o vacío para obtener sus detalles.");
        }
        try {
            List<DetalleVenta> detalles = ventaDAO.obtenerDetalles(id);
            // Delega la conversión de la lista de entidades DetalleVenta a lista de DTOs
            return DetalleVentaMapper.toDTOList(detalles);
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al obtener los detalles de la venta con ID " + id + ": " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Error al obtener los detalles de la venta.");
            throw new NegocioException("Error al obtener los detalles de la venta: " + e.getMessage(), e);
        }
    }
}
