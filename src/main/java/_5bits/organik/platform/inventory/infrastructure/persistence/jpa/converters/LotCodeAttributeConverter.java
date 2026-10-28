package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.inventory.domain.model.valueobjects.LotCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class LotCodeAttributeConverter implements AttributeConverter<LotCode, String> {
    @Override
    public String convertToDatabaseColumn(LotCode attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public LotCode convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new LotCode(dbData);
    }
}
