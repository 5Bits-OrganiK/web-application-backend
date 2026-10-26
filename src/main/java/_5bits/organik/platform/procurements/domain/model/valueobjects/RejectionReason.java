package _5bits.organik.platform.procurements.domain.model.valueobjects;

public record RejectionReason(String value) {
    private static final int MAX_LENGTH = 512;
    private static final String NOT_BLANK_MESSAGE_KEY = "shipmentOrder.error.rejectionReason.notBlank";
    private static final String SIZE_MESSAGE_KEY = "shipmentOrder.error.rejectionReason.size";

    public RejectionReason {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
    }

}
