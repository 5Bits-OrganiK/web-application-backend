package _5bits.organik.platform.inventory.domain.model.commands;

import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementReason;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementType;
import _5bits.organik.platform.inventory.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;

public record RegisterInventoryMovementCommand(StockLotId stockLotId, MovementType type, Quantity quantity,
                                               MovementReason reason) {
    public RegisterInventoryMovementCommand {
        if (stockLotId == null) {
            throw new IllegalArgumentException("StockLotId cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("MovementType cannot be null");
        }
        if (quantity == null || !quantity.isPositive()) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (type.requiresReason() && reason == null) {
            throw new IllegalArgumentException("MovementReason is required for adjustments and waste");
        }
    }
}
