package _5bits.organik.platform.communication.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationTitle;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NotificationTitleConverter implements AttributeConverter<NotificationTitle, String> {
    @Override
    public String convertToDatabaseColumn(NotificationTitle attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public NotificationTitle convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new NotificationTitle(dbData);
    }
}
