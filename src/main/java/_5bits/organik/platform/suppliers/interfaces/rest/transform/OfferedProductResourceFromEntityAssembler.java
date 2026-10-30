package _5bits.organik.platform.suppliers.interfaces.rest.transform;

import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.OfferedProductResource;

public class OfferedProductResourceFromEntityAssembler {
    public static OfferedProductResource toResourceFromEntity(OfferedProduct entity) {
        return new OfferedProductResource(
                entity.getId(),
                entity.getSupplierId().value(),
                entity.getProductId().value(),
                entity.getLotCode().value(),
                entity.getAvailableQuantity().value(),
                entity.getExpiresOn());
    }
}
