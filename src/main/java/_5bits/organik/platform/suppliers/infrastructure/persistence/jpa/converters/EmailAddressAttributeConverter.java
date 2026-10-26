package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.EmailAddress;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EmailAddressAttributeConverter implements AttributeConverter<EmailAddress, String> {
    @Override
    public String convertToDatabaseColumn(EmailAddress attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public EmailAddress convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new EmailAddress(dbData);
    }
}
