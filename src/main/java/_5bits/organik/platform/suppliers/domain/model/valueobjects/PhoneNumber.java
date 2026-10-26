package _5bits.organik.platform.suppliers.domain.model.valueobjects;

import java.util.regex.Pattern;

public record PhoneNumber(String value) {
    private static final int MAX_LENGTH = 20;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^\\+?[0-9][0-9 -]{5,19}$");
    private static final String NOT_BLANK_MESSAGE_KEY = "supplier.error.phoneNumber.notBlank";
    private static final String SIZE_MESSAGE_KEY = "supplier.error.phoneNumber.size";
    private static final String PATTERN_MESSAGE_KEY = "supplier.error.phoneNumber.pattern";

    public PhoneNumber {
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
