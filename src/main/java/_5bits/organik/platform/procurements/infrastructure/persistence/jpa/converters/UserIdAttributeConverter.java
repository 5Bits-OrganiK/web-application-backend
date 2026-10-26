package _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.procurements.domain.model.valueobjects.UserId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserIdAttributeConverter implements AttributeConverter<UserId, Long> {
    @Override
    public Long convertToDatabaseColumn(UserId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public UserId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new UserId(dbData);
    }
}
