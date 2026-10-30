package _5bits.organik.platform.products.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.products.domain.model.valueobjects.Sku;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SkuAttributeConverter implements AttributeConverter<Sku, String> {
    @Override
    public String convertToDatabaseColumn(Sku attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Sku convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Sku(dbData);
    }
}
