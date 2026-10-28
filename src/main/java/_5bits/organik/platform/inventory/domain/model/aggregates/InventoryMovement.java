package _5bits.organik.platform.inventory.domain.model.aggregates;

import _5bits.organik.platform.inventory.domain.model.commands.RegisterInventoryMovementCommand;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementReason;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementType;
import _5bits.organik.platform.inventory.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.MovementReasonAttributeConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.QuantityAttributeConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.StockLotIdAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class InventoryMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @Convert(converter = StockLotIdAttributeConverter.class)
    private StockLotId stockLotId;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private MovementType type;

    @Column(nullable = false, updatable = false)
    @Convert(converter = QuantityAttributeConverter.class)
    private Quantity quantity;

    @Column(updatable = false)
    @Convert(converter = MovementReasonAttributeConverter.class)
    private MovementReason reason;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    protected InventoryMovement() {}

    public InventoryMovement(RegisterInventoryMovementCommand command) {
        this.stockLotId = command.stockLotId();
        this.type = command.type();
        this.quantity = command.quantity();
        this.reason = command.reason();
    }
}
