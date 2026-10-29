package _5bits.organik.platform.profiles.domain.model.valueobjects;

import java.util.Set;

public record Language(String value) {
    private static final Set<String> ALLOWED_LANGUAGES = Set.of("EN", "ES");

    public Language {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Language cannot be null or blank");
        }
        value = value.trim().toUpperCase();
        if (!ALLOWED_LANGUAGES.contains(value)) {
            throw new IllegalArgumentException("Language must be EN or ES");
        }
    }
}
