package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.Quantity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class QuantityAttributeConverter implements AttributeConverter<Quantity, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Quantity attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Quantity convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new Quantity(dbData);
    }
}
