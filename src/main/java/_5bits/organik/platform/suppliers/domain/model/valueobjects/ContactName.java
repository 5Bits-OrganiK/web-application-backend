package _5bits.organik.platform.suppliers.domain.model.valueobjects;

public record ContactName(String value) {
    private static final int MAX_LENGTH = 256;
    private static final String NOT_BLANK_MESSAGE_KEY = "supplier.error.contactName.notBlank";
    private static final String SIZE_MESSAGE_KEY = "supplier.error.contactName.size";

    public ContactName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
    }

}
