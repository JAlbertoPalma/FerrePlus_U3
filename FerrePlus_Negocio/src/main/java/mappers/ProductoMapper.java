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
 * Clase utilitaria para la conversión entre la entidad {@link Producto}
 * y sus respectivos Data Transfer Objects (DTOs): {@link ProductoViejoDTO}
 * y {@link ProductoNuevoDTO}. Esta clase proporciona métodos estáticos
 * para facilitar la transformación de datos entre las capas de la aplicación.
 * 
 * @author Beto_
 */
public class ProductoMapper {
    
    /**
     * Convierte una entidad {@link Producto} a un {@link ProductoViejoDTO}.
     * Si la entidad Producto es nula, retorna nulo.
     *
     * @param producto La entidad Producto a convertir.
     * @return Un nuevo ProductoViejoDTO con los datos de la entidad, o nulo si la entidad es nula.
     */
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

    /**
     * Convierte un {@link ProductoViejoDTO} a una entidad {@link Producto}.
     * Si el DTO es nulo, retorna nulo. Intenta convertir el ID del DTO
     * a un {@link ObjectId}; si falla, imprime la traza de la excepción
     * y el ID de la entidad Producto será nulo.
     *
     * @param dto El ProductoViejoDTO a convertir.
     * @return Una nueva entidad Producto con los datos del DTO, o nulo si el DTO es nulo.
     */
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

    /**
     * Convierte un {@link ProductoNuevoDTO} a una entidad {@link Producto}.
     * Si el DTO es nulo, retorna nulo. La fecha y hora de alta se establece
     * a la fecha y hora actual del sistema, y el estado se establece a true por defecto.
     *
     * @param dto El ProductoNuevoDTO a convertir.
     * @return Una nueva entidad Producto con los datos del DTO, o nulo si el DTO es nulo.
     */
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
    
    /**
     * Convierte una lista de entidades {@link Producto} a una lista de {@link ProductoViejoDTO}.
     * Si la lista de productos es nula o vacía, retorna una lista vacía.
     *
     * @param productos La lista de entidades Producto a convertir.
     * @return Una nueva lista de ProductoViejoDTOs con los datos de las entidades,
     * o una lista vacía si la lista de productos es nula o vacía.
     */
    public static List<ProductoViejoDTO> toViejoDTOList(List<Producto> productos) {
        List<ProductoViejoDTO> dtos = new ArrayList<>();
        if (productos != null && !productos.isEmpty()) {
            for (Producto producto : productos) {
                dtos.add(toViejoDTO(producto));
            }
        }
        return dtos;
    }

    /**
     * Convierte una lista de {@link ProductoViejoDTO} a una lista de entidades {@link Producto}.
     * Si la lista de DTOs es nula o vacía, retorna una lista vacía.
     *
     * @param dtos La lista de ProductoViejoDTOs a convertir.
     * @return Una nueva lista de entidades Producto con los datos de los DTOs,
     * o una lista vacía si la lista de DTOs es nula o vacía.
     */
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
