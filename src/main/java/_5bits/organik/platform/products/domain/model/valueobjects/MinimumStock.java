package _5bits.organik.platform.products.domain.model.valueobjects;

public record MinimumStock(Integer value) {
    private static final String NOT_NULL_MESSAGE_KEY = "product.error.minimumStock.notNull";
    private static final String MIN_MESSAGE_KEY = "product.error.minimumStock.min";

    public MinimumStock {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE_KEY);
        }
        if (value < 0) {
            throw new IllegalArgumentException(MIN_MESSAGE_KEY);
        }
    }

}
