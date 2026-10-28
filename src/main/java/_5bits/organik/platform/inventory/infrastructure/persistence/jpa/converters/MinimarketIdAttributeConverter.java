package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.inventory.domain.model.valueobjects.MinimarketId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MinimarketIdAttributeConverter implements AttributeConverter<MinimarketId, String> {
    @Override
    public String convertToDatabaseColumn(MinimarketId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public MinimarketId convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new MinimarketId(dbData);
    }
}
