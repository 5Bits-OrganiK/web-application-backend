package _5bits.organik.platform.products.domain.model.valueobjects;

public record CategoryId(Long value) {
    private static final String NOT_NULL_MESSAGE_KEY = "product.error.categoryId.notNull";
    private static final String INVALID_MESSAGE_KEY = "product.error.categoryId.invalid";

    public CategoryId {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE_KEY);
        }
        if (value <= 0) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }

}
