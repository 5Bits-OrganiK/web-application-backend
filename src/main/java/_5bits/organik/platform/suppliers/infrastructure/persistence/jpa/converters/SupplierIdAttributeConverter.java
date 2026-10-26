package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SupplierIdAttributeConverter implements AttributeConverter<SupplierId, String> {
    @Override
    public String convertToDatabaseColumn(SupplierId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public SupplierId convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new SupplierId(dbData);
    }
}
