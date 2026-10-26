#!/usr/bin/env bash
set -e

BASE_PKG="_5bits.organik.platform"
BASE_DIR="src/main/java/_5bits/organik/platform"

git checkout develop
git checkout -b feature/conservation

CONSERVATION_VO_DIR="$BASE_DIR/conservation/domain/model/valueobjects"
mkdir -p "$CONSERVATION_VO_DIR"

# 1. ZoneName
cat <<EOF > "$CONSERVATION_VO_DIR/ZoneName.java"
package $BASE_PKG.conservation.domain.model.valueobjects;

public record ZoneName(String value) {
    public ZoneName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Zone name cannot be null or blank");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("Zone name cannot exceed 100 characters");
        }
    }
}
EOF
git add "$CONSERVATION_VO_DIR/ZoneName.java"
git commit -m "feat(storage-zone): add ZoneName value object"

# 2. StorageCondition
cat <<EOF > "$CONSERVATION_VO_DIR/StorageCondition.java"
package $BASE_PKG.conservation.domain.model.valueobjects;

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
EOF
git add "$CONSERVATION_VO_DIR/StorageCondition.java"
git commit -m "feat(storage-zone): add StorageCondition value object"

# 3. Temperature
cat <<EOF > "$CONSERVATION_VO_DIR/Temperature.java"
package $BASE_PKG.conservation.domain.model.valueobjects;

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
EOF
git add "$CONSERVATION_VO_DIR/Temperature.java"
git commit -m "feat(storage-zone): add Temperature value object"

echo "Bloque 1 completado. Puedes tomarte un descanso y ejecutar el Bloque 2 cuando gustes."