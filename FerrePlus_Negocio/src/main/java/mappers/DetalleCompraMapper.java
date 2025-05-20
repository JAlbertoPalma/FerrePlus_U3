/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import DTO.DetalleCompraDTO;
import entidades.DetalleCompra;
import excepciones.NegocioException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

/**
 *
 * @author Beto_
 */
public class DetalleCompraMapper {
    /**
     * Convierte una entidad {@link DetalleCompra} a un {@link DetalleCompraDTO}.
     * Si la entidad es nula, retorna nulo.
     *
     * @param detalle La entidad DetalleCompra a convertir.
     * @return Un nuevo DetalleCompraDTO con los datos de la entidad, o nulo si la entidad es nula.
     */
    public static DetalleCompraDTO toDTO(DetalleCompra detalle) {
        if (detalle == null) {
            return null;
        }
        return new DetalleCompraDTO(
                detalle.getIdProducto() != null ? detalle.getIdProducto().toHexString() : null,
                detalle.getCantidad(),
                detalle.getPrecioDeCompra(),
                detalle.getSubtotal()
        );
    }

    /**
     * Convierte un {@link DetalleCompraDTO} a una entidad {@link DetalleCompra}.
     * Si el DTO es nulo, retorna nulo. Intenta convertir el ID del producto a {@link ObjectId}.
     *
     * @param dto El DetalleCompraDTO a convertir.
     * @return Una nueva entidad DetalleCompra con los datos del DTO, o nulo si el DTO es nulo.
     * @throws NegocioException Si el idProducto en el DTO es inválido y no se puede convertir a ObjectId.
     */
    public static DetalleCompra toEntity(DetalleCompraDTO dto) throws NegocioException {
        if (dto == null) {
            return null;
        }
        ObjectId idProducto = null;
        if (dto.getIdProducto() != null && !dto.getIdProducto().isEmpty()) {
            try {
                idProducto = new ObjectId(dto.getIdProducto());
            } catch (IllegalArgumentException e) {
                // Podrías loggear este error y luego re-lanzar una NegocioException
                throw new NegocioException("ID de producto inválido en el detalle de compra: " + dto.getIdProducto(), e);
            }
        }
        return new DetalleCompra(
                idProducto,
                dto.getCantidad(),
                dto.getPrecioDeCompra(),
                dto.getSubtotal()
        );
    }

    /**
     * Convierte una lista de entidades {@link DetalleCompra} a una lista de {@link DetalleCompraDTO}.
     *
     * @param detalles Lista de entidades DetalleCompra.
     * @return Lista de DetalleCompraDTO, o una lista vacía si la entrada es nula o vacía.
     */
    public static List<DetalleCompraDTO> toDTOList(List<DetalleCompra> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            return new ArrayList<>();
        }
        return detalles.stream()
                .map(DetalleCompraMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de {@link DetalleCompraDTO} a una lista de entidades {@link DetalleCompra}.
     *
     * @param dtos Lista de DetalleCompraDTO.
     * @return Lista de DetalleCompra, o una lista vacía si la entrada es nula o vacía.
     * @throws NegocioException Si algún ID de producto en los DTOs es inválido.
     */
    public static List<DetalleCompra> toEntityList(List<DetalleCompraDTO> dtos) throws NegocioException {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<DetalleCompra> entities = new ArrayList<>();
        for (DetalleCompraDTO dto : dtos) {
            entities.add(toEntity(dto));
        }
        return entities;
    }
}
