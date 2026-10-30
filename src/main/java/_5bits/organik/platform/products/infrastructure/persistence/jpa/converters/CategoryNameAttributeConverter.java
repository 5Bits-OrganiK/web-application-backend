package _5bits.organik.platform.products.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.products.domain.model.valueobjects.CategoryName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CategoryNameAttributeConverter implements AttributeConverter<CategoryName, String> {
    @Override
    public String convertToDatabaseColumn(CategoryName attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public CategoryName convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new CategoryName(dbData);
    }
}
