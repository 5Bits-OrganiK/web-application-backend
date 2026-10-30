package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.interfaces.rest.resources.InventoryMovementResource;

public class InventoryMovementResourceFromEntityAssembler {
    public static InventoryMovementResource toResourceFromEntity(InventoryMovement entity) {
        return new InventoryMovementResource(
                entity.getId(),
                entity.getStockLotId().value(),
                entity.getType().name(),
                entity.getQuantity().value(),
                entity.getReason() == null ? null : entity.getReason().value(),
                entity.getCreatedAt());
    }
}
