package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementReason;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MovementReasonAttributeConverter implements AttributeConverter<MovementReason, String> {
    @Override
    public String convertToDatabaseColumn(MovementReason attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public MovementReason convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new MovementReason(dbData);
    }
}
