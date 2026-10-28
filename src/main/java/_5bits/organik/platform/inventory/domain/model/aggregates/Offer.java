package _5bits.organik.platform.inventory.domain.model.aggregates;

import _5bits.organik.platform.inventory.domain.model.commands.CreateOfferCommand;
import _5bits.organik.platform.inventory.domain.model.valueobjects.OfferPrice;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.OfferPriceAttributeConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.StockLotIdAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = StockLotIdAttributeConverter.class)
    private StockLotId stockLotId;

    @Column(nullable = false, precision = 10, scale = 2)
    @Convert(converter = OfferPriceAttributeConverter.class)
    private OfferPrice price;

    @Column(nullable = false)
    private LocalDate startsOn;

    @Column(nullable = false)
    private LocalDate endsOn;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    protected Offer() {}

    public Offer(CreateOfferCommand command) {
        this.stockLotId = command.stockLotId();
        this.price = command.price();
        this.startsOn = command.startsOn();
        this.endsOn = command.endsOn();
    }

    public boolean isActiveOn(LocalDate date) {
        return !date.isBefore(startsOn) && !date.isAfter(endsOn);
    }
}
