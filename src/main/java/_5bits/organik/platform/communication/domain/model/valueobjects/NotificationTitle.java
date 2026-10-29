package _5bits.organik.platform.communication.domain.model.valueobjects;

public record NotificationTitle(String value) {
    public NotificationTitle {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Notification title cannot be null or blank");
        }
        value = value.trim();
        if (value.length() > 150) {
            throw new IllegalArgumentException("Notification title cannot exceed 150 characters");
        }
    }
}
