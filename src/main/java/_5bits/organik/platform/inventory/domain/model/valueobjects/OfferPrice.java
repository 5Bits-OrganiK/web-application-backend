package _5bits.organik.platform.inventory.domain.model.valueobjects;

import java.math.BigDecimal;

public record OfferPrice(BigDecimal value) {
    private static final int MAX_SCALE = 2;
    private static final String NOT_NULL_MESSAGE_KEY = "inventory.error.offerPrice.notNull";
    private static final String POSITIVE_MESSAGE_KEY = "inventory.error.offerPrice.positive";
    private static final String SCALE_MESSAGE_KEY = "inventory.error.offerPrice.scale";

    public OfferPrice {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE_KEY);
        }
        if (value.signum() <= 0) {
            throw new IllegalArgumentException(POSITIVE_MESSAGE_KEY);
        }
        if (value.stripTrailingZeros().scale() > MAX_SCALE) {
            throw new IllegalArgumentException(SCALE_MESSAGE_KEY);
        }
    }

}
