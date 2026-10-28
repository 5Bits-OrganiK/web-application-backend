package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.inventory.domain.model.valueobjects.OfferPrice;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigDecimal;

@Converter
public class OfferPriceAttributeConverter implements AttributeConverter<OfferPrice, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(OfferPrice attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public OfferPrice convertToEntityAttribute(BigDecimal dbData) {
        return dbData == null ? null : new OfferPrice(dbData);
    }
}
