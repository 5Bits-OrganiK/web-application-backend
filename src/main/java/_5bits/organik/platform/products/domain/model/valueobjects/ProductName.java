package _5bits.organik.platform.products.domain.model.valueobjects;

public record ProductName(String value) {
    private static final int MAX_LENGTH = 256;
    private static final String NOT_BLANK_MESSAGE_KEY = "product.error.name.notBlank";
    private static final String SIZE_MESSAGE_KEY = "product.error.name.size";

    public ProductName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
    }

}
