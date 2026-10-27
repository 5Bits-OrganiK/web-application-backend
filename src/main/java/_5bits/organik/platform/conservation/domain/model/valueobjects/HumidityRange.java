package _5bits.organik.platform.conservation.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record HumidityRange(
        @Column(name = "min_humidity", nullable = false) Humidity min,
        @Column(name = "max_humidity", nullable = false) Humidity max
) {
    public HumidityRange {
        if (min == null || max == null) {
            throw new IllegalArgumentException("Minimum and maximum humidity cannot be null");
        }
        if (min.value() > max.value()) {
            throw new IllegalArgumentException("Minimum humidity cannot be greater than maximum humidity");
        }
    }

    public boolean includes(Humidity humidity) {
        return humidity.value() >= min.value() && humidity.value() <= max.value();
    }
}
