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
 *
 * @author Beto_
 */
public class VentaMapper {
    // Convierte una entidad Venta a un VentaViejoDTO
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

    // Convierte un VentaViejoDTO a una entidad Venta
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

    // Convierte un VentaNuevoDTO a una entidad Venta
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

    // Convierte una lista de entidades Venta a una lista de VentaViejoDTO
    public static List<VentaViejoDTO> toViejoDTOList(List<Venta> ventas) {
        return ventas.stream()
                .map(VentaMapper::toViejoDTO)
                .collect(Collectors.toList());
    }

    // Convierte una lista de VentaViejoDTO a una lista de entidades Venta
    public static List<Venta> toEntityList(List<VentaViejoDTO> dtos) {
        return dtos.stream()
                .map(VentaMapper::toEntity)
                .collect(Collectors.toList());
    }

    // Métodos auxiliares para DetalleVenta
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
