/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import entidades.Compra;
import java.util.List;
import java.util.stream.Collectors;
import nuevosDTOs.CompraNuevoDTO;
import org.bson.types.ObjectId;
import viejosDTOs.CompraViejoDTO;

/**
 *
 * @author Beto_
 */
public class CompraMapper {
    // Convierte una entidad Compra a un CompraViejoDTO
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

    // Convierte un CompraViejoDTO a una entidad Compra
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
    
    // Convierte un CompraNuevoDTO a una entidad Compra
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
                        .map(CompraMapper::NuevotoDetalleEntity)
                        .collect(Collectors.toList())
        );
    }

    // Convierte una lista de entidades Compra a una lista de CompraViejoDTO
    public static List<CompraViejoDTO> toViejoDTOList(List<Compra> compras) {
        return compras.stream()
                .map(CompraMapper::toViejoDTO)
                .collect(Collectors.toList());
    }

    // Convierte una lista de CompraViejoDTO a una lista de entidades Compra
    public static List<Compra> toEntityList(List<CompraViejoDTO> dtos) {
        return dtos.stream()
                .map(CompraMapper::toEntity)
                .collect(Collectors.toList());
    }

    // Métodos auxiliares para DetalleCompra
    private static CompraViejoDTO.DetalleCompraViejoDTO toDetalleViejoDTO(Compra.DetalleCompra detalleCompra) {
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

    private static Compra.DetalleCompra toDetalleEntity(CompraViejoDTO.DetalleCompraViejoDTO dto) {
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
        return new Compra.DetalleCompra(
                idProducto,
                dto.getCantidad(),
                dto.getPrecioDeCompra(),
                dto.getSubtotal()
        );
    }
    
    private static Compra.DetalleCompra NuevotoDetalleEntity(CompraNuevoDTO.DetalleCompraNuevoDTO dto) {
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
        return new Compra.DetalleCompra(
                idProducto,
                dto.getCantidad(),
                dto.getPrecioDeCompra(),
                dto.getSubtotal()
        );
    }
}
