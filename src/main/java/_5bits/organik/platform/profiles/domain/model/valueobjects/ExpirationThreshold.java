package _5bits.organik.platform.profiles.domain.model.valueobjects;

public record ExpirationThreshold(Integer days) {
    public ExpirationThreshold {
        if (days == null || days < 1 || days > 365) {
            throw new IllegalArgumentException("Expiration threshold must be between 1 and 365 days");
        }
    }
}
