package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.conservation.domain.model.valueobjects.ZoneName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ZoneNameConverter implements AttributeConverter<ZoneName, String> {
    @Override
    public String convertToDatabaseColumn(ZoneName attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public ZoneName convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new ZoneName(dbData);
    }
}
