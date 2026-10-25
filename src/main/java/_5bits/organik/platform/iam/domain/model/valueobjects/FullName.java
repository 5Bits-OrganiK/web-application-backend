package _5bits.organik.platform.iam.domain.model.valueobjects;

public record FullName(String value) {
    private static final int MAX_LENGTH = 256;
    private static final String NOT_BLANK_MESSAGE_KEY = "user.error.fullName.notBlank";
    private static final String SIZE_MESSAGE_KEY = "user.error.fullName.size";

    public FullName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
    }

}
