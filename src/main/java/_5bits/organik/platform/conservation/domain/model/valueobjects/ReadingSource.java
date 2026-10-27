package _5bits.organik.platform.conservation.domain.model.valueobjects;

import java.util.Set;

public record ReadingSource(String value) {
    private static final Set<String> ALLOWED_SOURCES = Set.of("SENSOR", "MANUAL");

    public ReadingSource {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Reading source cannot be null or blank");
        }
        value = value.trim().toUpperCase();
        if (!ALLOWED_SOURCES.contains(value)) {
            throw new IllegalArgumentException("Reading source must be SENSOR or MANUAL");
        }
    }
}
