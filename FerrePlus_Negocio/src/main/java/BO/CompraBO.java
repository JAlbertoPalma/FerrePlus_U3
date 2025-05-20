/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DTO.CompraDTO;
import DTO.DetalleCompraDTO;
import Interfaces.ICompraBO;
import entidades.Compra;
import entidades.DetalleCompra;
import entidades.Producto;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.CompraMapper;
import mappers.DetalleCompraMapper;
import modulo.compras.ICompraDAO;
import modulo.inventario.IProductoDAO;

/**
 *
 * @author Beto_
 */
public class CompraBO implements ICompraBO{
    
    private ICompraDAO compraDAO;
    private IProductoDAO productoDAO;
    private static AtomicInteger folioCounter = new AtomicInteger(0);

    public CompraBO() {
        try {
            this.productoDAO = modulo.inventario.ProductoDAO.getInstanceDAO();
            this.compraDAO = modulo.compras.CompraDAO.getInstanceDAO();
        } catch (PersistenciaException ex) {
            Logger.getLogger(CompraBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String generarFolio() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String fechaParte = now.format(formatter);
        // Incrementar el contador y formatearlo a 3 dígitos con ceros a la izquierda
        // (esto es básico, para producción se necesita un mecanismo de secuencia persistente)
        int numeroConsecutivo = folioCounter.incrementAndGet();
        return String.format("CP-%s-%03d", fechaParte, numeroConsecutivo);
    }
    
    @Override
    public CompraDTO agregar(CompraDTO compraDTO) throws NegocioException {
        // 1. Validaciones de Negocio
        if (compraDTO == null) {
            throw new NegocioException("La información de la compra no puede ser nula.");
        }
        if (compraDTO.getFecha() == null) {
            throw new NegocioException("La fecha de compra es obligatoria.");
        }
        if (compraDTO.getDetalles() == null || compraDTO.getDetalles().isEmpty()) {
            throw new NegocioException("Una compra debe tener al menos un producto en sus detalles.");
        }

        double totalCalculado = 0.0;

        // Validar y calcular subtotales de cada detalle
        for (DetalleCompraDTO detalle : compraDTO.getDetalles()) {
            if (detalle.getIdProducto() == null || detalle.getIdProducto().trim().isEmpty()) {
                throw new NegocioException("El ID del producto en el detalle no puede ser nulo o vacío.");
            }
            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) { // Evitar cantidades negativas o cero
                throw new NegocioException("La cantidad adquirida para un producto debe ser positiva.");
            }
            if (detalle.getPrecioDeCompra() == null || detalle.getPrecioDeCompra() < 0) { // Evitar precios negativos
                throw new NegocioException("El precio de compra unitario no puede ser negativo.");
            }

            // Regla: Validar que cada producto esté previamente registrado en el inventario.
            Producto productoExistente;
            try {
                productoExistente = productoDAO.obtenerPorId(detalle.getIdProducto());
            } catch (PersistenciaException e) {
                throw new NegocioException("Error al verificar la existencia del producto con ID " + detalle.getIdProducto() + ": " + e.getMessage(), e);
            }
            if (productoExistente == null) {
                throw new NegocioException("El producto con ID " + detalle.getIdProducto() + " no está registrado en el inventario.");
            }

            // Regla: Calcular subtotal automáticamente por producto
            double subtotalDetalle = detalle.getCantidad() * detalle.getPrecioDeCompra();
            detalle.setSubtotal(subtotalDetalle);
            totalCalculado += subtotalDetalle;
        }

        // Regla: Calcular automáticamente el total de la compra.
        compraDTO.setTotal(totalCalculado);

        // Regla: Generar un folio único con el formato CP-YYYYMMDD-XXX
        compraDTO.setFolio(generarFolio());

        // Regla: Registrar automáticamente la fecha y hora del registro de la operación (ya la tienes en el DTO o se establece en el DAO)
        compraDTO.setFecha(LocalDate.now());
        // 2. Convertir DTO a Entidad utilizando el Mapper
        Compra compraEntity;
        try {
            compraEntity = CompraMapper.toEntity(compraDTO);
        } catch (NegocioException e) {
            throw new NegocioException("Error al mapear la compra a entidad: " + e.getMessage(), e);
        }

        // 3. Llamar a la capa de persistencia para agregar la compra
        Compra compraGuardada;
        try {
            compraGuardada = compraDAO.agregar(compraEntity);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al agregar la compra en la base de datos: " + e.getMessage(), e);
        }

        // 4. Actualizar stock y precio referencial de productos (Reglas de Negocio Post-confirmación)
        // Esto ya lo hace tu DAO, pero lo incluyo aquí para visualizar el flujo.
        // Si se prefiere que el BO maneje esto directamente (y no el DAO), moverías esa lógica aquí.
        /*
        try {
            for (DetalleCompra detalle : compraGuardada.getDetalles()) { // Usar los detalles de la compra guardada si el DAO los modifica (ej. IDs)
                Producto producto = productoDAO.obtenerPorId(detalle.getIdProducto().toHexString());
                if (producto == null) { // Esto no debería pasar si la validación previa fue correcta.
                    throw new NegocioException("Producto no encontrado después de agregar compra: " + detalle.getIdProducto().toHexString());
                }

                // Actualizar precio de compra referencial si es diferente
                if (!detalle.getPrecioDeCompra().equals(producto.getPrecioCompraReferencial())) {
                    producto.setPrecioCompraReferencial(detalle.getPrecioDeCompra());
                }
                // Incrementar stock
                producto.setStock(producto.getStock() + detalle.getCantidad());

                productoDAO.actualizar(producto);
            }
        } catch (PersistenciaException e) {
            // Esto es una situación compleja. La compra se agregó, pero el inventario no se actualizó.
            // En un sistema real, esto requeriría transacciones o mecanismos de compensación.
            throw new NegocioException("La compra se registró, pero hubo un error al actualizar el inventario: " + e.getMessage(), e);
        }
        */

        // 5. Convertir la entidad guardada (con el ID asignado por la BD) a DTO y retornar
        return CompraMapper.toDTO(compraGuardada);
    }
    
    @Override
    public CompraDTO obtenerPorId(String id) throws NegocioException {
        if (id == null || id.trim().isEmpty()) {
            throw new NegocioException("El ID de la compra no puede ser nulo o vacío para la consulta.");
        }
        try {
            Compra compraEntity = compraDAO.obtenerPorId(id);
            return CompraMapper.toDTO(compraEntity); // Maneja null si no se encuentra
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener la compra por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public CompraDTO obtenerPorFolio(String folio) throws NegocioException {
        if (folio == null || folio.trim().isEmpty()) {
            throw new NegocioException("El folio de la compra no puede ser nulo o vacío para la consulta.");
        }
        try {
            Compra compraEntity = compraDAO.obtenerPorFolio(folio);
            return CompraMapper.toDTO(compraEntity); // Maneja null si no se encuentra
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener la compra por folio: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CompraDTO> obtenerTodas() throws NegocioException {
        try {
            List<Compra> compras = compraDAO.obtenerTodas();
            return CompraMapper.toDTOList(compras);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener todas las compras: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CompraDTO> obtenerPorFiltros(LocalDate fechaInicio, LocalDate fechaFin, String proveedor, String nombreProducto) throws NegocioException {
        // Aquí no hay validaciones de "negocio" complejas sobre los filtros, solo se pasan al DAO.
        // El DAO ya maneja la lógica de qué filtros aplicar.
        try {
            List<Compra> comprasFiltradas = compraDAO.obtenerPorFiltros(fechaInicio, fechaFin, proveedor, nombreProducto);
            return CompraMapper.toDTOList(comprasFiltradas);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener compras por filtros: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DetalleCompraDTO> obtenerDetalles(String id) throws NegocioException {
        if (id == null || id.trim().isEmpty()) {
            throw new NegocioException("El ID de la compra no puede ser nulo o vacío para obtener sus detalles.");
        }
        try {
            List<DetalleCompra> detalles = compraDAO.obtenerDetalles(id);
            return DetalleCompraMapper.toDTOList(detalles);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener los detalles de la compra: " + e.getMessage(), e);
        }
    }
}
