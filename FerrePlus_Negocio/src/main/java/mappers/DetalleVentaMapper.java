/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import DTO.DetalleVentaDTO;
import com.mongodb.internal.diagnostics.logging.Logger;
import entidades.DetalleVenta;
import excepciones.NegocioException;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

/**
 *
 * @author Beto_
 */
public class DetalleVentaMapper {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentaMapper.class.getName());

    /**
     * Convierte una entidad {@link DetalleVenta} a un {@link DetalleVentaDTO}.
     * Si el detalle de venta es nulo, retorna nulo.
     *
     * @param detalleVenta El detalle de venta a convertir.
     * @return Un nuevo DetalleVentaDTO con los datos del detalle, o nulo si el detalle es nulo.
     */
    public static DetalleVentaDTO toDTO(DetalleVenta detalleVenta) {
        if (detalleVenta == null) {
            return null;
        }
        return new DetalleVentaDTO(
                detalleVenta.getIdProducto() != null ? detalleVenta.getIdProducto().toHexString() : null,
                detalleVenta.getCantidad(),
                detalleVenta.getDescuento(),
                detalleVenta.getSubtotal()
        );
    }

    /**
     * Convierte un {@link DetalleVentaDTO} a una entidad {@link DetalleVenta}.
     * Si el DTO del detalle es nulo, retorna nulo. Intenta convertir el ID del producto
     * a un {@link ObjectId}; si falla, loggea y lanza una {@link NegocioException}.
     *
     * @param dto El DetalleVentaDTO a convertir.
     * @return Una nueva entidad DetalleVenta con los datos del DTO, o nulo si el DTO es nulo.
     * @throws NegocioException Si el ID del producto en el DTO es inválido.
     */
    public static DetalleVenta toEntity(DetalleVentaDTO dto) throws NegocioException {
        if (dto == null) {
            return null;
        }
        ObjectId idProducto = null;
        if (dto.getIdProducto() != null && !dto.getIdProducto().isEmpty()) {
            try {
                idProducto = new ObjectId(dto.getIdProducto());
            } catch (IllegalArgumentException e) {
                throw new NegocioException("ID de producto inválido en el detalle de venta: " + dto.getIdProducto(), e);
            }
        }
        return new DetalleVenta(
                idProducto,
                dto.getCantidad(),
                dto.getDescuento(),
                dto.getSubtotal()
        );
    }

    /**
     * Convierte una lista de entidades {@link DetalleVenta} a una lista de {@link DetalleVentaDTO}.
     * Si la lista de detalles es nula, retorna una lista vacía.
     *
     * @param detalles La lista de entidades DetalleVenta a convertir.
     * @return Una nueva lista de DetalleVentaDTOs con los datos de las entidades,
     * o una lista vacía si la lista de detalles es nula.
     */
    public static List<DetalleVentaDTO> toDTOList(List<DetalleVenta> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            return new ArrayList<>();
        }
        return detalles.stream()
                .map(DetalleVentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de {@link DetalleVentaDTO} a una lista de entidades {@link DetalleVenta}.
     * Si la lista de DTOs es nula, retorna una lista vacía.
     *
     * @param dtos La lista de DetalleVentaDTOs a convertir.
     * @return Una nueva lista de entidades DetalleVenta con los datos de los DTOs,
     * o una lista vacía si la lista de DTOs es nula.
     * @throws NegocioException Si algún ID de producto en los DTOs es inválido.
     */
    public static List<DetalleVenta> toEntityList(List<DetalleVentaDTO> dtos) throws NegocioException {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<DetalleVenta> entities = new ArrayList<>();
        for (DetalleVentaDTO dto : dtos) {
            entities.add(toEntity(dto)); // Esto podría lanzar NegocioException
        }
        return entities;
    }
}
