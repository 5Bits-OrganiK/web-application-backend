package _5bits.organik.platform.communication.domain.model.valueobjects;

import java.util.Set;

public record Severity(String value) {
    private static final Set<String> ALLOWED_SEVERITIES = Set.of("INFO", "WARNING", "CRITICAL");

    public Severity {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Severity cannot be null or blank");
        }
        value = value.trim().toUpperCase();
        if (!ALLOWED_SEVERITIES.contains(value)) {
            throw new IllegalArgumentException("Severity must be INFO, WARNING, or CRITICAL");
        }
    }
}
