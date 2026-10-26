package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.OrganicCertification;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class OrganicCertificationAttributeConverter implements AttributeConverter<OrganicCertification, String> {
    @Override
    public String convertToDatabaseColumn(OrganicCertification attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public OrganicCertification convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new OrganicCertification(dbData);
    }
}
