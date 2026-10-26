package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.PhoneNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PhoneNumberAttributeConverter implements AttributeConverter<PhoneNumber, String> {
    @Override
    public String convertToDatabaseColumn(PhoneNumber attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public PhoneNumber convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new PhoneNumber(dbData);
    }
}
