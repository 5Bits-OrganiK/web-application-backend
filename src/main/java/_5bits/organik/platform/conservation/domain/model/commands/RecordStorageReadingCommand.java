package _5bits.organik.platform.conservation.domain.model.commands;

import _5bits.organik.platform.conservation.domain.model.valueobjects.Humidity;
import _5bits.organik.platform.conservation.domain.model.valueobjects.ReadingSource;
import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageZoneId;
import _5bits.organik.platform.conservation.domain.model.valueobjects.Temperature;
import java.time.LocalDateTime;

public record RecordStorageReadingCommand(
        StorageZoneId storageZoneId,
        Temperature temperature,
        Humidity humidity,
        LocalDateTime recordedAt,
        ReadingSource source
) {
    public RecordStorageReadingCommand {
        if (storageZoneId == null || temperature == null || humidity == null || recordedAt == null || source == null) {
            throw new IllegalArgumentException("All storage reading parameters are required");
        }
    }
}
