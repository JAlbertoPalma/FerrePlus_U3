/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import entidades.Compra;
import java.util.List;
import java.util.stream.Collectors;
import BO.CompraNuevoDTO;
import org.bson.types.ObjectId;
import DTO.CompraViejoDTO;
import entidades.DetalleCompra;

/**
 * Clase utilitaria para la conversión entre la entidad {@link Compra}
 * y sus respectivos Data Transfer Objects (DTOs): {@link CompraViejoDTO}
 * y {@link CompraNuevoDTO}. También incluye métodos para la conversión
 * de la entidad anidada {@link Compra.DetalleCompra} y sus DTOs.
 * Proporciona métodos estáticos para facilitar la transformación de datos
 * entre las capas de la aplicación.
 * 
 * @author Beto_
 */
public class CompraMapper {
    
    /**
     * Convierte una entidad {@link Compra} a un {@link CompraViejoDTO}.
     * Incluye la conversión de la lista de detalles de compra.
     * Si la entidad Compra es nula, retorna nulo.
     *
     * @param compra La entidad Compra a convertir.
     * @return Un nuevo CompraViejoDTO con los datos de la entidad, o nulo si la entidad es nula.
     */
    public static CompraViejoDTO toViejoDTO(Compra compra) {
        if (compra == null) {
            return null;
        }
        return new CompraViejoDTO(
                compra.getId() != null ? compra.getId().toHexString() : null,
                compra.getFolio(),
                compra.getFecha(),
                compra.getTotal(),
                compra.getProveedor(),
                
                compra.getDetalles().stream()
                        .map(CompraMapper::toDetalleViejoDTO)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Convierte un {@link CompraViejoDTO} a una entidad {@link Compra}.
     * Incluye la conversión de la lista de detalles de compra.
     * Si el DTO es nulo, retorna nulo. Intenta convertir el ID del DTO
     * a un {@link ObjectId}; si falla, imprime la traza de la excepción
     * y el ID de la entidad Compra será nulo.
     *
     * @param dto El CompraViejoDTO a convertir.
     * @return Una nueva entidad Compra con los datos del DTO, o nulo si el DTO es nulo.
     */
    public static Compra toEntity(CompraViejoDTO dto) {
        if (dto == null) {
            return null;
        }
        ObjectId id = null;
        if (dto.getId() != null && !dto.getId().isEmpty()) {
            try {
                id = new ObjectId(dto.getId());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return new Compra(
                id,
                dto.getFolio(),
                dto.getFecha(),
                dto.getTotal(),
                dto.getProveedor(),
                dto.getDetalles().stream()
                        .map(CompraMapper::toDetalleEntity)
                        .collect(Collectors.toList())
        );
    }
    
    /**
     * Convierte un {@link CompraNuevoDTO} a una entidad {@link Compra}.
     * Incluye la conversión de la lista de detalles de compra.
     * Si el DTO es nulo, retorna nulo.
     *
     * @param dto El CompraNuevoDTO a convertir.
     * @return Una nueva entidad Compra con los datos del DTO, o nulo si el DTO es nulo.
     */
    public static Compra toEntity(CompraNuevoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Compra(
                dto.getFolio(),
                dto.getFecha(),
                dto.getTotal(),
                dto.getProveedor(),
                dto.getDetalles().stream()
                        .map(CompraMapper::NuevoToDetalleEntity)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Convierte una lista de entidades {@link Compra} a una lista de {@link CompraViejoDTO}.
     * Utiliza streams para realizar la conversión de manera concisa.
     * Si la lista de compras es nula o vacía, retorna una lista vacía.
     *
     * @param compras La lista de entidades Compra a convertir.
     * @return Una nueva lista de CompraViejoDTOs con los datos de las entidades,
     * o una lista vacía si la lista de compras es nula o vacía.
     */
    public static List<CompraViejoDTO> toViejoDTOList(List<Compra> compras) {
        return compras.stream()
                .map(CompraMapper::toViejoDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de {@link CompraViejoDTO} a una lista de entidades {@link Compra}.
     * Utiliza streams para realizar la conversión de manera concisa.
     * Si la lista de DTOs es nula o vacía, retorna una lista vacía.
     *
     * @param dtos La lista de CompraViejoDTOs a convertir.
     * @return Una nueva lista de entidades Compra con los datos de los DTOs,
     * o una lista vacía si la lista de DTOs es nula o vacía.
     */
    public static List<Compra> toEntityList(List<CompraViejoDTO> dtos) {
        return dtos.stream()
                .map(CompraMapper::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad {@link Compra.DetalleCompra} a un
     * {@link CompraViejoDTO.DetalleCompraViejoDTO}.
     * Si el detalle de compra es nulo, retorna nulo.
     *
     * @param detalleCompra El detalle de compra a convertir.
     * @return Un nuevo DetalleCompraViejoDTO con los datos del detalle, o nulo si el detalle es nulo.
     */
    private static CompraViejoDTO.DetalleCompraViejoDTO toDetalleViejoDTO(DetalleCompra detalleCompra) {
        if (detalleCompra == null) {
            return null;
        }
        return new CompraViejoDTO.DetalleCompraViejoDTO(
                detalleCompra.getIdProducto() != null ? detalleCompra.getIdProducto().toHexString() : null,
                detalleCompra.getCantidad(),
                detalleCompra.getPrecioDeCompra(),
                detalleCompra.getSubtotal()
        );
    }
    
    /**
     * Convierte un {@link CompraViejoDTO.DetalleCompraViejoDTO} a una entidad
     * {@link Compra.DetalleCompra}.
     * Si el DTO del detalle es nulo, retorna nulo. Intenta convertir el ID del producto
     * a un {@link ObjectId}; si falla, imprime la traza de la excepción y el ID del
     * producto en el detalle será nulo.
     *
     * @param dto El DetalleCompraViejoDTO a convertir.
     * @return Una nueva entidad DetalleCompra con los datos del DTO, o nulo si el DTO es nulo.
     */
    private static DetalleCompra toDetalleEntity(CompraViejoDTO.DetalleCompraViejoDTO dto) {
        if (dto == null) {
            return null;
        }
        ObjectId idProducto = null;
        if (dto.getIdProducto() != null && !dto.getIdProducto().isEmpty()) {
            try {
                idProducto = new ObjectId(dto.getIdProducto());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
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
     * Convierte un {@link CompraNuevoDTO.DetalleCompraNuevoDTO} a una entidad
     * {@link Compra.DetalleCompra}.
     * Si el DTO del detalle es nulo, retorna nulo. Intenta convertir el ID del producto
     * a un {@link ObjectId}; si falla, imprime la traza de la excepción y el ID del
     * producto en el detalle será nulo.
     *
     * @param dto El DetalleCompraNuevoDTO a convertir.
     * @return Una nueva entidad DetalleCompra con los datos del DTO, o nulo si el DTO es nulo.
     */
    private static DetalleCompra NuevoToDetalleEntity(CompraNuevoDTO.DetalleCompraNuevoDTO dto) {
        if (dto == null) {
            return null;
        }
        ObjectId idProducto = null;
        if (dto.getIdProducto() != null && !dto.getIdProducto().isEmpty()) {
            try {
                idProducto = new ObjectId(dto.getIdProducto());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return new DetalleCompra(
                idProducto,
                dto.getCantidad(),
                dto.getPrecioDeCompra(),
                dto.getSubtotal()
        );
    }
}
