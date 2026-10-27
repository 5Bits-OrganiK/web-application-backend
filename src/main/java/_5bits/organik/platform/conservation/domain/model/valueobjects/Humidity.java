package _5bits.organik.platform.conservation.domain.model.valueobjects;

public record Humidity(Double value) {
    public Humidity {
        if (value == null || value.isNaN()) {
            throw new IllegalArgumentException("Humidity value cannot be null");
        }
        if (value < 0.0 || value > 100.0) {
            throw new IllegalArgumentException("Humidity must be between 0.0 and 100.0 percent");
        }
    }
}
