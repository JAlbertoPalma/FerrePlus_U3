/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conversores;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Beto_
 */
public class FechaCvr {
    
    /**
     * Convierte un java.time.LocalDate a java.util.Date.
     * La hora del Date resultante se establece a medianoche (00:00:00)
     * en la zona horaria por defecto del sistema.
     *
     * @param localDate La fecha LocalDate a convertir.
     * @return El objeto Date resultante, o null si localDate es null.
     */
    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        LocalDateTime localDateTime = localDate.atStartOfDay();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(defaultZoneId).toInstant();
        return Date.from(instant);
    }

    /**
     * Convierte un java.util.Date a java.time.LocalDate, utilizando
     * la zona horaria por defecto del sistema.
     *
     * @param date El objeto Date a convertir.
     * @return La fecha LocalDate resultante, o null si date es null.
     */
    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return instant.atZone(defaultZoneId).toLocalDate();
    }
    
    /**
     * Convierte un java.time.LocalDateTime a java.util.Date, utilizando
     * la zona horaria por defecto del sistema.
     *
     * @param localDateTime La fecha y hora LocalDateTime a convertir.
     * @return El objeto Date resultante, o null si localDateTime es null.
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(defaultZoneId).toInstant();
        return Date.from(instant);
    }

    /**
     * Convierte un java.util.Date a java.time.LocalDateTime, utilizando
     * la zona horaria por defecto del sistema.
     *
     * @param date El objeto Date a convertir.
     * @return La fecha y hora LocalDateTime resultante, o null si date es null.
     */
    public static LocalDateTime toDateTime(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, defaultZoneId);
    }
}
