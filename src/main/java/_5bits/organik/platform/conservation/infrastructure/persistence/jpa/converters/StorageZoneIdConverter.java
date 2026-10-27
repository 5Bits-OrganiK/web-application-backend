package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageZoneId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StorageZoneIdConverter implements AttributeConverter<StorageZoneId, Long> {
    @Override
    public Long convertToDatabaseColumn(StorageZoneId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public StorageZoneId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new StorageZoneId(dbData);
    }
}
