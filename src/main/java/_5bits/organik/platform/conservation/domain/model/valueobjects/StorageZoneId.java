package _5bits.organik.platform.conservation.domain.model.valueobjects;

public record StorageZoneId(Long value) {
    public StorageZoneId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("StorageZoneId must be a positive number");
        }
    }
}
