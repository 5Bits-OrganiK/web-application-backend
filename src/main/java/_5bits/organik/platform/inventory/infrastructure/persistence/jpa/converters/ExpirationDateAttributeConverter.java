package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.inventory.domain.model.valueobjects.ExpirationDate;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDate;

@Converter
public class ExpirationDateAttributeConverter implements AttributeConverter<ExpirationDate, LocalDate> {
    @Override
    public LocalDate convertToDatabaseColumn(ExpirationDate attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public ExpirationDate convertToEntityAttribute(LocalDate dbData) {
        return dbData == null ? null : new ExpirationDate(dbData);
    }
}
