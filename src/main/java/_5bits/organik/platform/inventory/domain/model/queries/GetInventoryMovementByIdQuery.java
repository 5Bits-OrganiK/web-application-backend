package _5bits.organik.platform.inventory.domain.model.queries;

public record GetInventoryMovementByIdQuery(Long id) {
    public GetInventoryMovementByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("inventoryMovement.error.id.invalid");
    }
}
