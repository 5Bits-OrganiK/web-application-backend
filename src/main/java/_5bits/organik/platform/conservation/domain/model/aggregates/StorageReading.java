package _5bits.organik.platform.conservation.domain.model.aggregates;

import _5bits.organik.platform.conservation.domain.model.valueobjects.Humidity;
import _5bits.organik.platform.conservation.domain.model.valueobjects.ReadingSource;
import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageZoneId;
import _5bits.organik.platform.conservation.domain.model.valueobjects.Temperature;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "storage_readings")
public class StorageReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "storage_zone_id", nullable = false)
    private StorageZoneId storageZoneId;

    @Column(name = "temperature", nullable = false)
    private Temperature temperature;

    @Column(name = "humidity", nullable = false)
    private Humidity humidity;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @Column(name = "source", nullable = false, length = 20)
    private ReadingSource source;

    protected StorageReading() {
    }

    public StorageReading(StorageZoneId storageZoneId, Temperature temperature, Humidity humidity, LocalDateTime recordedAt, ReadingSource source) {
        if (recordedAt == null) {
            throw new IllegalArgumentException("Recorded date and time cannot be null");
        }
        this.storageZoneId = storageZoneId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.recordedAt = recordedAt;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public StorageZoneId getStorageZoneId() {
        return storageZoneId;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public ReadingSource getSource() {
        return source;
    }
}
