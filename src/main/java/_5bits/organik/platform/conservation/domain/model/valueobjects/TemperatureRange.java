package _5bits.organik.platform.conservation.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record TemperatureRange(
        @Column(name = "min_temperature", nullable = false) Temperature min,
        @Column(name = "max_temperature", nullable = false) Temperature max
) {
    public TemperatureRange {
        if (min == null || max == null) {
            throw new IllegalArgumentException("Minimum and maximum temperature cannot be null");
        }
        if (min.value() > max.value()) {
            throw new IllegalArgumentException("Minimum temperature cannot be greater than maximum temperature");
        }
    }

    public boolean includes(Temperature temperature) {
        return temperature.value() >= min.value() && temperature.value() <= max.value();
    }
}
