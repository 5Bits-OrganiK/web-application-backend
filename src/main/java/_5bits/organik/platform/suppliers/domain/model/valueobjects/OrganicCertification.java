package _5bits.organik.platform.suppliers.domain.model.valueobjects;

import java.util.regex.Pattern;

public record OrganicCertification(String value) {
    private static final int MAX_LENGTH = 64;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[a-zA-Z0-9 .-]+$");
    private static final String NOT_BLANK_MESSAGE_KEY = "supplier.error.organicCertification.notBlank";
    private static final String SIZE_MESSAGE_KEY = "supplier.error.organicCertification.size";
    private static final String PATTERN_MESSAGE_KEY = "supplier.error.organicCertification.pattern";

    public OrganicCertification {
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
