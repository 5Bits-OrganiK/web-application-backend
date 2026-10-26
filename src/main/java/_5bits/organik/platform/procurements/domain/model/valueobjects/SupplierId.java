package _5bits.organik.platform.procurements.domain.model.valueobjects;

import java.util.regex.Pattern;

public record SupplierId(String value) {
    private static final int MAX_LENGTH = 256;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");
    private static final String NOT_BLANK_MESSAGE_KEY = "shipmentOrder.error.supplierId.notBlank";
    private static final String SIZE_MESSAGE_KEY = "shipmentOrder.error.supplierId.size";
    private static final String PATTERN_MESSAGE_KEY = "shipmentOrder.error.supplierId.pattern";

    public SupplierId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
        if (!ALLOWED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(PATTERN_MESSAGE_KEY);
        }
    }

}
