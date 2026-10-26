package _5bits.organik.platform.requisition.domain.model.valueobjects;

public record RequestReason(String value) {
    private static final int MAX_LENGTH = 512;
    private static final String NOT_BLANK_MESSAGE_KEY = "supplyRequest.error.requestReason.notBlank";
    private static final String SIZE_MESSAGE_KEY = "supplyRequest.error.requestReason.size";

    public RequestReason {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
    }

}
