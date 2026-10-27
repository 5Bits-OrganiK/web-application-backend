package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageCondition;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StorageConditionConverter implements AttributeConverter<StorageCondition, String> {
    @Override
    public String convertToDatabaseColumn(StorageCondition attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public StorageCondition convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new StorageCondition(dbData);
    }
}
