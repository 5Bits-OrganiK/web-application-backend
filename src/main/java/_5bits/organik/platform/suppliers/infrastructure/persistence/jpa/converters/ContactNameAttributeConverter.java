package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.ContactName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ContactNameAttributeConverter implements AttributeConverter<ContactName, String> {
    @Override
    public String convertToDatabaseColumn(ContactName attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public ContactName convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new ContactName(dbData);
    }
}
