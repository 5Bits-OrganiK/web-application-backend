package _5bits.organik.platform.conservation.domain.model.aggregates;

import _5bits.organik.platform.conservation.domain.model.valueobjects.Humidity;
import _5bits.organik.platform.conservation.domain.model.valueobjects.HumidityRange;
import _5bits.organik.platform.conservation.domain.model.valueobjects.StorageCondition;
import _5bits.organik.platform.conservation.domain.model.valueobjects.Temperature;
import _5bits.organik.platform.conservation.domain.model.valueobjects.TemperatureRange;
import _5bits.organik.platform.conservation.domain.model.valueobjects.ZoneName;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "storage_zones")
public class StorageZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private ZoneName name;

    @Column(name = "storage_condition", nullable = false, length = 30)
    private StorageCondition condition;

    @Embedded
    private TemperatureRange temperatureRange;

    @Embedded
    private HumidityRange humidityRange;

    protected StorageZone() {
    }

    public StorageZone(ZoneName name, StorageCondition condition, TemperatureRange temperatureRange, HumidityRange humidityRange) {
        this.name = name;
        this.condition = condition;
        this.temperatureRange = temperatureRange;
        this.humidityRange = humidityRange;
    }

    public boolean isReadingWithinRange(Temperature temperature, Humidity humidity) {
        return this.temperatureRange.includes(temperature) && this.humidityRange.includes(humidity);
    }

    public void updateAllowedRanges(TemperatureRange temperatureRange, HumidityRange humidityRange) {
        this.temperatureRange = temperatureRange;
        this.humidityRange = humidityRange;
    }

    public Long getId() {
        return id;
    }

    public ZoneName getName() {
        return name;
    }

    public StorageCondition getCondition() {
        return condition;
    }

    public TemperatureRange getTemperatureRange() {
        return temperatureRange;
    }

    public HumidityRange getHumidityRange() {
        return humidityRange;
    }
}
