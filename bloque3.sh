#!/usr/bin/env bash
set -e

BASE_PKG="_5bits.organik.platform"
BASE_DIR="src/main/java/_5bits/organik/platform"

CONSERVATION_VO_DIR="$BASE_DIR/conservation/domain/model/valueobjects"

# 4. Humidity
cat <<EOF > "$CONSERVATION_VO_DIR/Humidity.java"
package $BASE_PKG.conservation.domain.model.valueobjects;

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
EOF
git add "$CONSERVATION_VO_DIR/Humidity.java"
git commit -m "feat(storage-zone): add Humidity value object"

# 5. TemperatureRange
cat <<EOF > "$CONSERVATION_VO_DIR/TemperatureRange.java"
package $BASE_PKG.conservation.domain.model.valueobjects;

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
EOF
git add "$CONSERVATION_VO_DIR/TemperatureRange.java"
git commit -m "feat(storage-zone): add TemperatureRange value object"

# 6. HumidityRange
cat <<EOF > "$CONSERVATION_VO_DIR/HumidityRange.java"
package $BASE_PKG.conservation.domain.model.valueobjects;

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
EOF
git add "$CONSERVATION_VO_DIR/HumidityRange.java"
git commit -m "feat(storage-zone): add HumidityRange value object"

echo "Bloque 2 completado."