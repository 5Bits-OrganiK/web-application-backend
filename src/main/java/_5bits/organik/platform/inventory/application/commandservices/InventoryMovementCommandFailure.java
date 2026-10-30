package _5bits.organik.platform.inventory.application.commandservices;

public sealed interface InventoryMovementCommandFailure
        permits InventoryMovementCommandFailure.StockLotNotFound, InventoryMovementCommandFailure.InsufficientStock {
    String messageKey();

    record StockLotNotFound() implements InventoryMovementCommandFailure {
        @Override
        public String messageKey() {
            return "inventoryMovement.error.stockLotNotFound";
        }
    }

    record InsufficientStock() implements InventoryMovementCommandFailure {
        @Override
        public String messageKey() {
            return "inventoryMovement.error.insufficientStock";
        }
    }
}
