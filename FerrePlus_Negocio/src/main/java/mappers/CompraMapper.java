/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import entidades.Compra;
import java.util.List;
import java.util.stream.Collectors;
import util.CompraNuevoDTO;
import org.bson.types.ObjectId;
import DTO.CompraDTO;
import DTO.DetalleCompraDTO;
import entidades.DetalleCompra;
import excepciones.NegocioException;
import java.util.ArrayList;

/**
 * Clase utilitaria para la conversión entre la entidad {@link Compra}
 * y sus respectivos Data Transfer Objects (DTOs): {@link CompraDTO}
 * y {@link CompraNuevoDTO}. También incluye métodos para la conversión
 * de la entidad anidada {@link Compra.DetalleCompra} y sus DTOs.
 * Proporciona métodos estáticos para facilitar la transformación de datos
 * entre las capas de la aplicación.
 * 
 * @author Beto_
 */
public class CompraMapper {
    /**
     * Convierte una entidad {@link Compra} a un {@link CompraDTO}.
     * Si la entidad es nula, retorna nulo.
     *
     * @param compra La entidad Compra a convertir.
     * @return Un nuevo CompraDTO con los datos de la entidad, o nulo si la entidad es nula.
     */
    public static CompraDTO toDTO(Compra compra) {
        if (compra == null) {
            return null;
        }
        // Mapear la lista de detalles también
        List<DetalleCompraDTO> detallesDTO = DetalleCompraMapper.toDTOList(compra.getDetalles());

        return new CompraDTO(
                compra.getId() != null ? compra.getId().toHexString() : null,
                compra.getFolio(),
                compra.getFecha(),
                compra.getTotal(),
                compra.getProveedor(),
                detallesDTO
        );
    }

    /**
     * Convierte un {@link CompraDTO} a una entidad {@link Compra}.
     * Si el DTO es nulo, retorna nulo. Intenta convertir el ID de la compra a {@link ObjectId}.
     *
     * @param dto El CompraDTO a convertir.
     * @return Una nueva entidad Compra con los datos del DTO, o nulo si el DTO es nulo.
     * @throws NegocioException Si el ID en el DTO es inválido o si hay problemas en los detalles.
     */
    public static Compra toEntity(CompraDTO dto) throws NegocioException {
        if (dto == null) {
            return null;
        }
        ObjectId id = null;
        if (dto.getId() != null && !dto.getId().isEmpty()) {
            try {
                id = new ObjectId(dto.getId());
            } catch (IllegalArgumentException e) {
                throw new NegocioException("ID de compra inválido: " + dto.getId(), e);
            }
        }
        // Mapear la lista de detalles también
        List<DetalleCompra> detallesEntity = DetalleCompraMapper.toEntityList(dto.getDetalles());

        // Usar el constructor con ID para reconstruir la entidad si ya tiene uno
        if (id != null) {
            return new Compra(
                    id,
                    dto.getFolio(),
                    dto.getFecha(),
                    dto.getTotal(),
                    dto.getProveedor(),
                    detallesEntity
            );
        } else {
            // Usar el constructor sin ID si es una nueva compra
            return new Compra(
                    dto.getFolio(),
                    dto.getFecha(),
                    dto.getTotal(),
                    dto.getProveedor(),
                    detallesEntity
            );
        }
    }

    /**
     * Convierte una lista de entidades {@link Compra} a una lista de {@link CompraDTO}.
     *
     * @param compras Lista de entidades Compra.
     * @return Lista de CompraDTO, o una lista vacía si la entrada es nula o vacía.
     */
    public static List<CompraDTO> toDTOList(List<Compra> compras) {
        if (compras == null || compras.isEmpty()) {
            return new ArrayList<>();
        }
        return compras.stream()
                .map(CompraMapper::toDTO)
                .collect(Collectors.toList());
    }
}
