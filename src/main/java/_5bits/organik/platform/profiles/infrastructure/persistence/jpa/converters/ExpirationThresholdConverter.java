package _5bits.organik.platform.profiles.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.profiles.domain.model.valueobjects.ExpirationThreshold;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ExpirationThresholdConverter implements AttributeConverter<ExpirationThreshold, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ExpirationThreshold attribute) {
        return attribute == null ? null : attribute.days();
    }

    @Override
    public ExpirationThreshold convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new ExpirationThreshold(dbData);
    }
}
