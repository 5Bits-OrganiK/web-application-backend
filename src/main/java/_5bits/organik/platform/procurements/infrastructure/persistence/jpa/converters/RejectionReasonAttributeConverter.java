package _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.procurements.domain.model.valueobjects.RejectionReason;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RejectionReasonAttributeConverter implements AttributeConverter<RejectionReason, String> {
    @Override
    public String convertToDatabaseColumn(RejectionReason attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public RejectionReason convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new RejectionReason(dbData);
    }
}
