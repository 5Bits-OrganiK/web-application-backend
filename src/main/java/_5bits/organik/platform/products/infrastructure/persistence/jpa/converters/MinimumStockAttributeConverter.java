package _5bits.organik.platform.products.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.products.domain.model.valueobjects.MinimumStock;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MinimumStockAttributeConverter implements AttributeConverter<MinimumStock, Integer> {
    @Override
    public Integer convertToDatabaseColumn(MinimumStock attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public MinimumStock convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new MinimumStock(dbData);
    }
}
