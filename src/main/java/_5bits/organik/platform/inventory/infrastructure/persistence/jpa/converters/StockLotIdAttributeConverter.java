package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters;

import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StockLotIdAttributeConverter implements AttributeConverter<StockLotId, Long> {
    @Override
    public Long convertToDatabaseColumn(StockLotId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public StockLotId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new StockLotId(dbData);
    }
}
