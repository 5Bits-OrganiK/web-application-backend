package _5bits.organik.platform.inventory.domain.model.valueobjects;

public record StockLotId(Long value) {
    private static final long MIN_VALUE = 1L;
    private static final String NOT_NULL_MESSAGE_KEY = "inventory.error.stockLotId.notNull";
    private static final String MIN_MESSAGE_KEY = "inventory.error.stockLotId.min";

    public StockLotId {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE_KEY);
        }
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException(MIN_MESSAGE_KEY);
        }
    }

}
