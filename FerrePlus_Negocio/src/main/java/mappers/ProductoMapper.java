/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import entidades.Producto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import nuevosDTOs.ProductoNuevoDTO;
import org.bson.types.ObjectId;
import viejosDTOs.ProductoViejoDTO;

/**
 *
 * @author Beto_
 */
public class ProductoMapper {
    // Convierte una entidad Producto a un ProductoViejoDTO
    public static ProductoViejoDTO toViejoDTO(Producto producto) {
        if (producto == null) {
            return null;
        }
        return new ProductoViejoDTO(
                //Similar a un if, si es true se ejecuta el "?" si no, el ":" omg
                producto.getId() != null ? producto.getId().toHexString() : null,
                producto.getSKU(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getUnidadMedida(),
                producto.getPrecioCompraReferencial(),
                producto.getPrecioVenta(),
                producto.getProveedor(),
                producto.getStock(),
                producto.getFechaHoraAlta(),
                producto.getEstado(),
                producto.getObservaciones()
        );
    }

    // Convierte un ProductoViejoDTO a una entidad Producto
    public static Producto toEntity(ProductoViejoDTO dto) {
        if (dto == null) {
            return null;
        }
        ObjectId id = null;
        if (dto.getId() != null && !dto.getId().isEmpty()) {
            try {
                id = new ObjectId(dto.getId());
            } catch (IllegalArgumentException e) {
                //En caso de una mala conversión
                e.printStackTrace();
            }
        }
        return new Producto(
                id,
                dto.getSKU(),
                dto.getNombre(),
                dto.getCategoria(),
                dto.getUnidadMedida(),
                dto.getPrecioCompraReferencial(),
                dto.getPrecioVenta(),
                dto.getProveedor(),
                dto.getStock(),
                dto.getFechaHoraAlta(),
                dto.getEstado(),
                dto.getObservaciones()
        );
    }

    // Convierte un ProductoNuevoDTO a una entidad Producto
    public static Producto toEntity(ProductoNuevoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Producto(
                dto.getSKU(),
                dto.getNombre(),
                dto.getCategoria(),
                dto.getUnidadMedida(),
                dto.getPrecioCompraReferencial(),
                dto.getPrecioVenta(),
                dto.getProveedor(),
                dto.getStock(),
                LocalDateTime.now(), //x defecto la del sistema
                true, // Por defecto a true
                dto.getObservaciones()
        );
    }
    
    // Convierte una lista de entidades Producto a una lista de ProductoViejoDTO
    public static List<ProductoViejoDTO> toViejoDTOList(List<Producto> productos) {
        List<ProductoViejoDTO> dtos = new ArrayList<>();
        if (productos != null && !productos.isEmpty()) {
            for (Producto producto : productos) {
                dtos.add(toViejoDTO(producto));
            }
        }
        return dtos;
    }

    // Convierte una lista de ProductoViejoDTO a una lista de entidades Producto
    public static List<Producto> toEntityList(List<ProductoViejoDTO> dtos) {
        List<Producto> productos = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (ProductoViejoDTO dto : dtos) {
                productos.add(toEntity(dto));
            }
        }
        return productos;
    }
}
