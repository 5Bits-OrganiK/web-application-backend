package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.conservation.domain.model.valueobjects.Humidity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class HumidityConverter implements AttributeConverter<Humidity, Double> {
    @Override
    public Double convertToDatabaseColumn(Humidity attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Humidity convertToEntityAttribute(Double dbData) {
        return dbData == null ? null : new Humidity(dbData);
    }
}
