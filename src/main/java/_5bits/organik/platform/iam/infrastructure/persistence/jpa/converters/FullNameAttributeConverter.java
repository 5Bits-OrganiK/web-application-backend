package _5bits.organik.platform.iam.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.iam.domain.model.valueobjects.FullName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class FullNameAttributeConverter implements AttributeConverter<FullName, String> {
    @Override
    public String convertToDatabaseColumn(FullName attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public FullName convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new FullName(dbData);
    }
}
