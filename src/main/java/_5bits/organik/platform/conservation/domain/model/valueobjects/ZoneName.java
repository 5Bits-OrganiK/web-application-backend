package _5bits.organik.platform.conservation.domain.model.valueobjects;

public record ZoneName(String value) {
    public ZoneName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Zone name cannot be null or blank");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("Zone name cannot exceed 100 characters");
        }
    }
}
