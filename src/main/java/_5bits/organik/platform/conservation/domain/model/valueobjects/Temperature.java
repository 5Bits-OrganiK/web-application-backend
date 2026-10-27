package _5bits.organik.platform.conservation.domain.model.valueobjects;

public record Temperature(Double value) {
    public Temperature {
        if (value == null || value.isNaN()) {
            throw new IllegalArgumentException("Temperature value cannot be null");
        }
        if (value < -50.0 || value > 60.0) {
            throw new IllegalArgumentException("Temperature must be between -50.0 and 60.0 Celsius");
        }
    }
}
