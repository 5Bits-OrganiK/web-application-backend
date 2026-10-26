package _5bits.organik.platform.requisition.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.requisition.domain.model.valueobjects.ProductId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ProductIdAttributeConverter implements AttributeConverter<ProductId, String> {
    @Override
    public String convertToDatabaseColumn(ProductId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public ProductId convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new ProductId(dbData);
    }
}
