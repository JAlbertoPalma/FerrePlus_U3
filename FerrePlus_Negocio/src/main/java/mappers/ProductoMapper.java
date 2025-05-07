/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import entidades.Producto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import BO.ProductoBO;
import org.bson.types.ObjectId;
import DTO.ProductoDTO;

/**
 * Clase utilitaria para la conversión entre la entidad {@link Producto}
 * y sus respectivos Data Transfer Objects (DTOs): {@link ProductoDTO}
 * y {@link ProductoBO}. Esta clase proporciona métodos estáticos
 * para facilitar la transformación de datos entre las capas de la aplicación.
 * 
 * @author Beto_
 */
public class ProductoMapper {
    
    /**
     * Convierte una entidad {@link Producto} a un {@link ProductoDTO}.
     * Si la entidad Producto es nula, retorna nulo.
     *
     * @param producto La entidad Producto a convertir.
     * @return Un nuevo ProductoViejoDTO con los datos de la entidad, o nulo si la entidad es nula.
     */
    public static ProductoDTO toDTO(Producto producto) {
        if (producto == null) {
            return null;
        }
        return new ProductoDTO(
                //Similar a un if, si es true se ejecuta el "?" si no, el ":" omg
                producto.getId() != null ? producto.getId().toHexString() : null,
                producto.getSku(),
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

    /**
     * Convierte un {@link ProductoDTO} a una entidad {@link Producto}.
     * Si el DTO es nulo, retorna nulo. Intenta convertir el ID del DTO
     * a un {@link ObjectId}; si falla, imprime la traza de la excepción
     * y el ID de la entidad Producto será nulo.
     *
     * @param dto El ProductoViejoDTO a convertir.
     * @return Una nueva entidad Producto con los datos del DTO, o nulo si el DTO es nulo.
     */
    public static Producto toEntity(ProductoDTO dto) {
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
    
    /**
     * Convierte una lista de entidades {@link Producto} a una lista de {@link ProductoDTO}.
     * Si la lista de productos es nula o vacía, retorna una lista vacía.
     *
     * @param productos La lista de entidades Producto a convertir.
     * @return Una nueva lista de ProductoViejoDTOs con los datos de las entidades,
     * o una lista vacía si la lista de productos es nula o vacía.
     */
    public static List<ProductoDTO> toDTOList(List<Producto> productos) {
        List<ProductoDTO> dtos = new ArrayList<>();
        if (productos != null && !productos.isEmpty()) {
            for (Producto producto : productos) {
                dtos.add(toDTO(producto));
            }
        }
        return dtos;
    }

    /**
     * Convierte una lista de {@link ProductoDTO} a una lista de entidades {@link Producto}.
     * Si la lista de DTOs es nula o vacía, retorna una lista vacía.
     *
     * @param dtos La lista de ProductoViejoDTOs a convertir.
     * @return Una nueva lista de entidades Producto con los datos de los DTOs,
     * o una lista vacía si la lista de DTOs es nula o vacía.
     */
    public static List<Producto> toEntityList(List<ProductoDTO> dtos) {
        List<Producto> productos = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (ProductoDTO dto : dtos) {
                productos.add(toEntity(dto));
            }
        }
        return productos;
    }
}
