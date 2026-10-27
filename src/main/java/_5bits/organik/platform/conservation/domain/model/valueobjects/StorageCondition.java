package _5bits.organik.platform.conservation.domain.model.valueobjects;

import java.util.Set;

public record StorageCondition(String value) {
    private static final Set<String> ALLOWED_CONDITIONS = Set.of("REFRIGERATED", "COOL", "AMBIENT", "FROZEN");

    public StorageCondition {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Storage condition cannot be null or blank");
        }
        value = value.trim().toUpperCase();
        if (!ALLOWED_CONDITIONS.contains(value)) {
            throw new IllegalArgumentException("Invalid storage condition: " + value);
        }
    }
}
