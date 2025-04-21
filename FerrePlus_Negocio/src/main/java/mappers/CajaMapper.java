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
 *
 * @author Beto_
 */
public class CajaMapper {
    // Convierte una entidad Caja a un CajaViejoDTO
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

    // Convierte un CajaViejoDTO a una entidad Caja
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

    // Convierte un CajaNuevoDTO a una entidad Caja (para la apertura)
    public static Caja toEntity(CajaNuevoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Caja(
                dto.getMontoInicial()
        );
    }

    // Convierte una lista de entidades Caja a una lista de CajaViejoDTO
    public static List<CajaViejoDTO> toViejoDTOList(List<Caja> cajas) {
        List<CajaViejoDTO> dtos = new ArrayList<>();
        if (cajas != null && !cajas.isEmpty()) {
            for (Caja caja : cajas) {
                dtos.add(toViejoDTO(caja));
            }
        }
        return dtos;
    }

    // Convierte una lista de CajaViejoDTO a una lista de entidades Caja
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
