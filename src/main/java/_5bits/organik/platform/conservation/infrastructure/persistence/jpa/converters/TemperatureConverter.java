package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.conservation.domain.model.valueobjects.Temperature;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TemperatureConverter implements AttributeConverter<Temperature, Double> {
    @Override
    public Double convertToDatabaseColumn(Temperature attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Temperature convertToEntityAttribute(Double dbData) {
        return dbData == null ? null : new Temperature(dbData);
    }
}
