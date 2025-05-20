/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import util.VentaNuevoDTO;
import entidades.Venta;
import java.util.List;
import java.util.stream.Collectors;
import DTO.DetalleVentaDTO;
import org.bson.types.ObjectId;
import DTO.VentaDTO;
import entidades.DetalleVenta;
import excepciones.NegocioException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase utilitaria para la conversión entre la entidad {@link Venta}
 * y sus respectivos Data Transfer Objects (DTOs): {@link VentaDTO}
 * y {@link VentaNuevoDTO}. También incluye métodos para la conversión
 * de la entidad anidada {@link Venta.DetalleVenta} y sus DTOs.
 * Proporciona métodos estáticos para facilitar la transformación de datos
 * entre las capas de la aplicación.
 * 
 * @author Beto_
 */
public class VentaMapper {
    private static final Logger logger = Logger.getLogger(VentaMapper.class.getName());

    /**
     * Convierte una entidad {@link Venta} a un {@link VentaDTO}.
     * Incluye la conversión de la lista de detalles de venta usando {@link DetalleVentaMapper}.
     * Si la entidad Venta es nula, retorna nulo.
     *
     * @param venta La entidad Venta a convertir.
     * @return Un nuevo VentaDTO con los datos de la entidad, o nulo si la entidad es nula.
     */
    public static VentaDTO toDTO(Venta venta) {
        if (venta == null) {
            return null;
        }
        return new VentaDTO(
                venta.getId() != null ? venta.getId().toHexString() : null,
                venta.getFolio(),
                venta.getFechaHora(),
                venta.getTotal(),
                venta.getEstado(),
                venta.getIdCaja() != null ? venta.getIdCaja().toHexString() : null,
                // Delega el mapeo de la lista de detalles al DetalleVentaMapper
                DetalleVentaMapper.toDTOList(venta.getDetalles())
        );
    }

    /**
     * Convierte un {@link VentaDTO} a una entidad {@link Venta}.
     * Incluye la conversión de la lista de detalles de venta usando {@link DetalleVentaMapper}.
     * Si el DTO es nulo, retorna nulo. Intenta convertir los IDs (venta y caja) a {@link ObjectId};
     * si falla, loggea y lanza una {@link NegocioException}.
     *
     * @param dto El VentaDTO a convertir.
     * @return Una nueva entidad Venta con los datos del DTO, o nulo si el DTO es nulo.
     * @throws NegocioException Si algún ID (de venta, caja o producto en detalles) es inválido.
     */
    public static Venta toEntity(VentaDTO dto) throws NegocioException {
        if (dto == null) {
            return null;
        }
        
        ObjectId id = null;
        if (dto.getId() != null && !dto.getId().isEmpty()) {
            try {
                id = new ObjectId(dto.getId());
            } catch (IllegalArgumentException e) {
                logger.log(Level.WARNING, "ID de venta inválido: " + dto.getId(), e);
                throw new NegocioException("ID de venta inválido: " + dto.getId(), e);
            }
        }

        ObjectId idCaja = null;
        if (dto.getIdCaja() != null && !dto.getIdCaja().isEmpty()) {
            try {
                idCaja = new ObjectId(dto.getIdCaja());
            } catch (IllegalArgumentException e) {
                logger.log(Level.WARNING, "ID de caja inválido: " + dto.getIdCaja(), e);
                throw new NegocioException("ID de caja inválido: " + dto.getIdCaja(), e);
            }
        }
        
        // Delega el mapeo de la lista de detalles al DetalleVentaMapper
        List<DetalleVenta> detallesEntity = DetalleVentaMapper.toEntityList(dto.getDetalles());

        // Usa el constructor con ID para reconstruir la entidad si ya tiene uno
        if (id != null) {
            return new Venta(
                    id,
                    dto.getFolio(),
                    dto.getFechaHora(),
                    dto.getTotal(),
                    dto.getEstado(),
                    idCaja,
                    detallesEntity
            );
        } else {
            // Usa el constructor sin ID si es una nueva venta (asumiendo que el constructor de Venta sin ID
            // no lo requiere y el ID será generado por la persistencia, o se asigna en el BO antes de guardar)
            return new Venta(
                    dto.getFolio(),
                    dto.getFechaHora(),
                    dto.getTotal(),
                    dto.getEstado(), // Estado se maneja en el DTO o se establece por defecto en la entidad
                    idCaja,
                    detallesEntity
            );
        }
    }

    /**
     * Convierte una lista de entidades {@link Venta} a una lista de {@link VentaDTO}.
     *
     * @param ventas Lista de entidades Venta.
     * @return Lista de VentaDTO, o una lista vacía si la entrada es nula o vacía.
     */
    public static List<VentaDTO> toDTOList(List<Venta> ventas) {
        if (ventas == null || ventas.isEmpty()) {
            return new ArrayList<>();
        }
        return ventas.stream()
                .map(VentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de {@link VentaDTO} a una lista de entidades {@link Venta}.
     *
     * @param dtos Lista de VentaDTO.
     * @return Lista de Venta, o una lista vacía si la entrada es nula o vacía.
     * @throws NegocioException Si algún ID en los DTOs (de venta, caja o producto) es inválido.
     */
    public static List<Venta> toEntityList(List<VentaDTO> dtos) throws NegocioException {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<Venta> entities = new ArrayList<>();
        for (VentaDTO dto : dtos) {
            entities.add(toEntity(dto)); // Esto podría lanzar NegocioException
        }
        return entities;
    }
}
