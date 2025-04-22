/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import entidades.Caja;
import java.util.ArrayList;
import java.util.List;
import nuevosDTOs.CajaNuevoDTO;
import org.bson.types.ObjectId;
import viejosDTOs.CajaViejoDTO;

/**
 * Clase utilitaria para la conversión entre la entidad {@link Caja}
 * y su Data Transfer Object (DTO) {@link CajaViejoDTO}, así como desde
 * {@link CajaNuevoDTO} para la creación de nuevas cajas (apertura).
 * Proporciona métodos estáticos para facilitar la transformación de datos
 * entre las capas de la aplicación.
 * 
 * @author Beto_
 */
public class CajaMapper {
    
    /**
     * Convierte una entidad {@link Caja} a un {@link CajaViejoDTO}.
     * Si la entidad Caja es nula, retorna nulo.
     *
     * @param caja La entidad Caja a convertir.
     * @return Un nuevo CajaViejoDTO con los datos de la entidad, o nulo si la entidad es nula.
     */
    public static CajaViejoDTO toViejoDTO(Caja caja) {
        if (caja == null) {
            return null;
        }
        return new CajaViejoDTO(
                caja.getId() != null ? caja.getId().toHexString() : null,
                caja.getFechaHoraApertura(),
                caja.getMontoInicial(),
                caja.getEstadoSesion(),
                caja.getTotalVentas(),
                caja.getCantidadDeProductos(),
                caja.getNumeroDeVentas(),
                caja.getFechaHoraCierre(),
                caja.getMontoFinalEstimado(),
                caja.getObservacionesCierre()
        );
    }

    /**
     * Convierte un {@link CajaViejoDTO} a una entidad {@link Caja}.
     * Si el DTO es nulo, retorna nulo. Intenta convertir el ID del DTO
     * a un {@link ObjectId}; si falla, imprime la traza de la excepción
     * y el ID de la entidad Caja será nulo.
     *
     * @param dto El CajaViejoDTO a convertir.
     * @return Una nueva entidad Caja con los datos del DTO, o nulo si el DTO es nulo.
     */
    public static Caja toEntity(CajaViejoDTO dto) {
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
        return new Caja(
                id,
                dto.getFechaHoraApertura(),
                dto.getMontoInicial(),
                dto.getEstadoSesion(),
                dto.getTotalVentas(),
                dto.getCantidadDeProductos(),
                dto.getNumeroDeVentas(),
                dto.getFechaHoraCierre(),
                dto.getMontoFinalEstimado(),
                dto.getObservacionesCierre()
        );
    }

    /**
     * Convierte un {@link CajaNuevoDTO} a una entidad {@link Caja}.
     * Este método se utiliza para la creación de una nueva caja (apertura),
     * por lo que solo se mapea el monto inicial. Otros atributos se inicializan
     * con valores por defecto o se gestionan en la lógica de negocio.
     * Si el DTO es nulo, retorna nulo.
     *
     * @param dto El CajaNuevoDTO a convertir.
     * @return Una nueva entidad Caja con el monto inicial del DTO, o nulo si el DTO es nulo.
     */
    public static Caja toEntity(CajaNuevoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Caja(
                dto.getMontoInicial()
        );
    }

    /**
     * Convierte una lista de entidades {@link Caja} a una lista de {@link CajaViejoDTO}.
     * Si la lista de cajas es nula o vacía, retorna una lista vacía.
     *
     * @param cajas La lista de entidades Caja a convertir.
     * @return Una nueva lista de CajaViejoDTOs con los datos de las entidades,
     * o una lista vacía si la lista de cajas es nula o vacía.
     */
    public static List<CajaViejoDTO> toViejoDTOList(List<Caja> cajas) {
        List<CajaViejoDTO> dtos = new ArrayList<>();
        if (cajas != null && !cajas.isEmpty()) {
            for (Caja caja : cajas) {
                dtos.add(toViejoDTO(caja));
            }
        }
        return dtos;
    }

    /**
     * Convierte una lista de {@link CajaViejoDTO} a una lista de entidades {@link Caja}.
     * Si la lista de DTOs es nula o vacía, retorna una lista vacía.
     *
     * @param dtos La lista de CajaViejoDTOs a convertir.
     * @return Una nueva lista de entidades Caja con los datos de los DTOs,
     * o una lista vacía si la lista de DTOs es nula o vacía.
     */
    public static List<Caja> toEntityList(List<CajaViejoDTO> dtos) {
        List<Caja> cajas = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CajaViejoDTO dto : dtos) {
                cajas.add(toEntity(dto));
            }
        }
        return cajas;
    }
}
