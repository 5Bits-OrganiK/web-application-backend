package _5bits.organik.platform.profiles.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.profiles.domain.model.valueobjects.Language;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LanguageConverter implements AttributeConverter<Language, String> {
    @Override
    public String convertToDatabaseColumn(Language attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Language convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Language(dbData);
    }
}
