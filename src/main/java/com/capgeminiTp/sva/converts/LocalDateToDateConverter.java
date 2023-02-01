package com.capgeminiTp.sva.converts;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Converter(autoApply = true)
public class LocalDateToDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate locDate) {
        return locDate == null ? null : this.convertToDateViaSqlDate(locDate);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
        return sqlDate == null ? null : this.convertToLocalDateViaInstant(sqlDate);
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}