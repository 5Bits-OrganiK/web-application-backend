package _5bits.organik.platform.communication.domain.model.valueobjects;

import java.util.Set;

public record NotificationType(String value) {
    private static final Set<String> ALLOWED_TYPES = Set.of(
            "EXPIRATION",
            "CONSERVATION",
            "LOW_STOCK",
            "REPLENISHMENT",
            "ORDER"
    );

    public NotificationType {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Notification type cannot be null or blank");
        }
        value = value.trim().toUpperCase();
        if (!ALLOWED_TYPES.contains(value)) {
            throw new IllegalArgumentException("Invalid notification type: " + value);
        }
    }
}
