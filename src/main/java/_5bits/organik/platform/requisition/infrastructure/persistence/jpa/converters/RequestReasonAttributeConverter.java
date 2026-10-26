package _5bits.organik.platform.requisition.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.requisition.domain.model.valueobjects.RequestReason;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RequestReasonAttributeConverter implements AttributeConverter<RequestReason, String> {
    @Override
    public String convertToDatabaseColumn(RequestReason attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public RequestReason convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new RequestReason(dbData);
    }
}
