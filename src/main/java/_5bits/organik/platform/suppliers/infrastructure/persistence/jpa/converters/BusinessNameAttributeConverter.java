package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.BusinessName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BusinessNameAttributeConverter implements AttributeConverter<BusinessName, String> {
    @Override
    public String convertToDatabaseColumn(BusinessName attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public BusinessName convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new BusinessName(dbData);
    }
}
