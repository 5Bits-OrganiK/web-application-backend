package _5bits.organik.platform.inventory.application.commandservices;

public sealed interface StockLotCommandFailure permits StockLotCommandFailure.Duplicate {
    String messageKey();

    record Duplicate() implements StockLotCommandFailure {
        @Override
        public String messageKey() {
            return "stockLot.error.duplicate";
        }
    }
}
