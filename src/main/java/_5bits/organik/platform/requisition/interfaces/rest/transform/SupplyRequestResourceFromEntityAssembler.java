package _5bits.organik.platform.requisition.interfaces.rest.transform;

import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.interfaces.rest.resources.SupplyRequestResource;

public class SupplyRequestResourceFromEntityAssembler {
    public static SupplyRequestResource toResourceFromEntity(SupplyRequest entity) {
        return new SupplyRequestResource(
                entity.getId(),
                entity.getProductId().value(),
                entity.getSupplierId().value(),
                entity.getMinimarketId().value(),
                entity.getQuantity().value(),
                entity.getReason().value(),
                entity.getRequiredDate(),
                entity.getPriority().name(),
                entity.getStatus().name());
    }
}
