package _5bits.organik.platform.conservation.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.conservation.domain.model.valueobjects.ReadingSource;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReadingSourceConverter implements AttributeConverter<ReadingSource, String> {
    @Override
    public String convertToDatabaseColumn(ReadingSource attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public ReadingSource convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new ReadingSource(dbData);
    }
}
