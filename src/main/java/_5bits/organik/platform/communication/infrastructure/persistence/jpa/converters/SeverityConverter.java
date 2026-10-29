package _5bits.organik.platform.communication.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.communication.domain.model.valueobjects.Severity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SeverityConverter implements AttributeConverter<Severity, String> {
    @Override
    public String convertToDatabaseColumn(Severity attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Severity convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Severity(dbData);
    }
}
