/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import entidades.Venta;
import java.util.List;
import java.util.stream.Collectors;
import nuevosDTOs.VentaNuevoDTO;
import org.bson.types.ObjectId;
import viejosDTOs.VentaViejoDTO;

/**
 * Clase utilitaria para la conversión entre la entidad {@link Venta}
 * y sus respectivos Data Transfer Objects (DTOs): {@link VentaViejoDTO}
 * y {@link VentaNuevoDTO}. También incluye métodos para la conversión
 * de la entidad anidada {@link Venta.DetalleVenta} y sus DTOs.
 * Proporciona métodos estáticos para facilitar la transformación de datos
 * entre las capas de la aplicación.
 * 
 * @author Beto_
 */
public class VentaMapper {
    
    /**
     * Convierte una entidad {@link Venta} a un {@link VentaViejoDTO}.
     * Incluye la conversión de la lista de detalles de venta.
     * Si la entidad Venta es nula, retorna nulo.
     *
     * @param venta La entidad Venta a convertir.
     * @return Un nuevo VentaViejoDTO con los datos de la entidad, o nulo si la entidad es nula.
     */
    public static VentaViejoDTO toViejoDTO(Venta venta) {
        if (venta == null) {
            return null;
        }
        return new VentaViejoDTO(
                venta.getId() != null ? venta.getId().toHexString() : null,
                venta.getFolio(),
                venta.getFechaHora(),
                venta.getTotal(),
                venta.getEstado(),
                venta.getIdCaja() != null ? venta.getIdCaja().toHexString() : null,
                venta.getDetalles().stream()
                        .map(VentaMapper::toDetalleViejoDTO)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Convierte un {@link VentaViejoDTO} a una entidad {@link Venta}.
     * Incluye la conversión de la lista de detalles de venta.
     * Si el DTO es nulo, retorna nulo. Intenta convertir los IDs del DTO
     * (venta y caja) a {@link ObjectId}; si falla, imprime la traza de la excepción
     * y el ID correspondiente de la entidad Venta será nulo.
     *
     * @param dto El VentaViejoDTO a convertir.
     * @return Una nueva entidad Venta con los datos del DTO, o nulo si el DTO es nulo.
     */
    public static Venta toEntity(VentaViejoDTO dto) {
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
        ObjectId idCaja = null;
        if (dto.getIdCaja() != null && !dto.getIdCaja().isEmpty()) {
            try {
                idCaja = new ObjectId(dto.getIdCaja());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return new Venta(
                id,
                dto.getFolio(),
                dto.getFechaHora(),
                dto.getTotal(),
                dto.getEstado(),
                idCaja,
                dto.getDetalles().stream()
                        .map(VentaMapper::toDetalleEntity)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Convierte un {@link VentaNuevoDTO} a una entidad {@link Venta}.
     * Incluye la conversión de la lista de detalles de venta.
     * Si el DTO es nulo, retorna nulo. Intenta convertir el ID de la caja del DTO
     * a un {@link ObjectId}; si falla, imprime la traza de la excepción
     * y el ID de la caja de la entidad Venta será nulo.
     *
     * @param dto El VentaNuevoDTO a convertir.
     * @return Una nueva entidad Venta con los datos del DTO, o nulo si el DTO es nulo.
     */
    public static Venta toEntity(VentaNuevoDTO dto) {
        if (dto == null) {
            return null;
        }
        ObjectId idCaja = null;
        if (dto.getIdCaja() != null && !dto.getIdCaja().isEmpty()) {
            try {
                idCaja = new ObjectId(dto.getIdCaja());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return new Venta(
                dto.getFolio(),
                dto.getFechaHora(),
                dto.getTotal(),
                dto.getEstado(),
                idCaja,
                dto.getDetalles().stream()
                        .map(VentaMapper::NuevoToDetalleEntity)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Convierte una lista de entidades {@link Venta} a una lista de {@link VentaViejoDTO}.
     * Si la lista de ventas es nula o vacía, retorna una lista vacía.
     *
     * @param ventas La lista de entidades Venta a convertir.
     * @return Una nueva lista de VentaViejoDTOs con los datos de las entidades,
     * o una lista vacía si la lista de ventas es nula o vacía.
     */
    public static List<VentaViejoDTO> toViejoDTOList(List<Venta> ventas) {
        return ventas.stream()
                .map(VentaMapper::toViejoDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de {@link VentaViejoDTO} a una lista de entidades {@link Venta}.
     * Si la lista de DTOs es nula o vacía, retorna una lista vacía.
     *
     * @param dtos La lista de VentaViejoDTOs a convertir.
     * @return Una nueva lista de entidades Venta con los datos de los DTOs,
     * o una lista vacía si la lista de DTOs es nula o vacía.
     */
    public static List<Venta> toEntityList(List<VentaViejoDTO> dtos) {
        return dtos.stream()
                .map(VentaMapper::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad {@link Venta.DetalleVenta} a un
     * {@link VentaViejoDTO.DetalleVentaViejoDTO}.
     * Si el detalle de venta es nulo, retorna nulo.
     *
     * @param detalleVenta El detalle de venta a convertir.
     * @return Un nuevo DetalleVentaViejoDTO con los datos del detalle, o nulo si el detalle es nulo.
     */
    private static VentaViejoDTO.DetalleVentaViejoDTO toDetalleViejoDTO(Venta.DetalleVenta detalleVenta) {
        if (detalleVenta == null) {
            return null;
        }
        return new VentaViejoDTO.DetalleVentaViejoDTO(
                detalleVenta.getIdProducto() != null ? detalleVenta.getIdProducto().toHexString() : null,
                detalleVenta.getCantidad(),
                detalleVenta.getDescuento(),
                detalleVenta.getSubtotal()
        );
    }
    
    /**
     * Convierte un {@link VentaViejoDTO.DetalleVentaViejoDTO} a una entidad
     * {@link Venta.DetalleVenta}.
     * Si el DTO del detalle es nulo, retorna nulo. Intenta convertir el ID del producto
     * a un {@link ObjectId}; si falla, imprime la traza de la excepción y el ID del
     * producto en el detalle será nulo.
     *
     * @param dto El DetalleVentaViejoDTO a convertir.
     * @return Una nueva entidad DetalleVenta con los datos del DTO, o nulo si el DTO es nulo.
     */
    private static Venta.DetalleVenta toDetalleEntity(VentaViejoDTO.DetalleVentaViejoDTO dto) {
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
        return new Venta.DetalleVenta(
                idProducto,
                dto.getCantidad(),
                dto.getDescuento(),
                dto.getSubtotal()
        );
    }
    
    /**
     * Convierte un {@link VentaNuevoDTO.DetalleVentaNuevoDTO} a una entidad
     * {@link Venta.DetalleVenta}.
     * Si el DTO del detalle es nulo, retorna nulo. Intenta convertir el ID del producto
     * a un {@link ObjectId}; si falla, imprime la traza de la excepción y el ID del
     * producto en el detalle será nulo.
     *
     * @param dto El DetalleVentaNuevoDTO a convertir.
     * @return Una nueva entidad DetalleVenta con los datos del DTO, o nulo si el DTO es nulo.
     */
    private static Venta.DetalleVenta NuevoToDetalleEntity(VentaNuevoDTO.DetalleVentaNuevoDTO dto) {
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
        return new Venta.DetalleVenta(
                idProducto,
                dto.getCantidad(),
                dto.getDescuento(),
                dto.getSubtotal()
        );
    }
}
