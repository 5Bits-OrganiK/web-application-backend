package _5bits.organik.platform.products.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.products.domain.model.valueobjects.CategoryId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CategoryIdAttributeConverter implements AttributeConverter<CategoryId, Long> {
    @Override
    public Long convertToDatabaseColumn(CategoryId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public CategoryId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new CategoryId(dbData);
    }
}
