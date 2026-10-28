package _5bits.organik.platform.inventory.domain.model.valueobjects;

public record Quantity(Integer value) {
    private static final int MIN_VALUE = 0;
    private static final String NOT_NULL_MESSAGE_KEY = "inventory.error.quantity.notNull";
    private static final String MIN_MESSAGE_KEY = "inventory.error.quantity.min";

    public Quantity {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE_KEY);
        }
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException(MIN_MESSAGE_KEY);
        }
    }

    public boolean isPositive() {
        return value > MIN_VALUE;
    }

    public boolean isLessThanOrEqualTo(Quantity other) {
        return value <= other.value();
    }

}
