package _5bits.organik.platform.products.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.products.domain.model.valueobjects.ProductName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ProductNameAttributeConverter implements AttributeConverter<ProductName, String> {
    @Override
    public String convertToDatabaseColumn(ProductName attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public ProductName convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new ProductName(dbData);
    }
}
