package _5bits.organik.platform.procurements.interfaces.rest.transform;

import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.interfaces.rest.resources.ShipmentOrderLineResource;
import _5bits.organik.platform.procurements.interfaces.rest.resources.ShipmentOrderResource;

public final class ShipmentOrderResourceFromEntityAssembler {
    private ShipmentOrderResourceFromEntityAssembler() {}

    public static ShipmentOrderResource toResourceFromEntity(ShipmentOrder entity) {
        var lines = entity.getLines().stream()
                .map(line -> new ShipmentOrderLineResource(
                        line.getId(),
                        line.getProductId().value(),
                        line.getQuantity().value(),
                        line.getLotCode().value(),
                        line.getExpiresOn()))
                .toList();
        return new ShipmentOrderResource(
                entity.getId(),
                entity.getSupplierId().value(),
                entity.getMinimarketId().value(),
                lines,
                entity.getStatus().name(),
                entity.getDecidedBy() == null ? null : entity.getDecidedBy().value(),
                entity.getDecidedAt(),
                entity.getRejectionReason() == null ? null : entity.getRejectionReason().value(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
x