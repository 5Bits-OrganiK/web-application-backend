package _5bits.organik.platform.conservation.domain.model.commands;

import _5bits.organik.platform.conservation.domain.model.valueobjects.HumidityRange;
import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageCondition;
import _5bits.organik.platform.conservation.domain.model.valueobjects.TemperatureRange;
import _5bits.organik.platform.conservation.domain.model.valueobjects.ZoneName;

public record CreateStorageZoneCommand(
        ZoneName name,
        StorageCondition condition,
        TemperatureRange temperatureRange,
        HumidityRange humidityRange
) {
    public CreateStorageZoneCommand {
        if (name == null || condition == null || temperatureRange == null || humidityRange == null) {
            throw new IllegalArgumentException("All storage zone parameters are required");
        }
    }
}
