package _5bits.organik.platform.requisition.domain.model.aggregates;

import _5bits.organik.platform.requisition.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.requisition.domain.model.valueobjects.RequestPriority;
import _5bits.organik.platform.requisition.domain.model.valueobjects.RequestReason;
import _5bits.organik.platform.requisition.domain.model.valueobjects.RequestStatus;
import _5bits.organik.platform.requisition.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.converters.MinimarketIdAttributeConverter;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.converters.ProductIdAttributeConverter;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.converters.QuantityAttributeConverter;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.converters.RequestReasonAttributeConverter;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.converters.SupplierIdAttributeConverter;
import _5bits.organik.platform.requisition.domain.model.commands.CreateSupplyRequestCommand;
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
public class SupplyRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = ProductIdAttributeConverter.class)
    private ProductId productId;

    @Column(nullable = false)
    @Convert(converter = SupplierIdAttributeConverter.class)
    private SupplierId supplierId;

    @Column(nullable = false)
    @Convert(converter = MinimarketIdAttributeConverter.class)
    private MinimarketId minimarketId;

    @Column(nullable = false)
    @Convert(converter = QuantityAttributeConverter.class)
    private Quantity quantity;

    @Column(nullable = false)
    @Convert(converter = RequestReasonAttributeConverter.class)
    private RequestReason reason;

    @Column(nullable = false)
    private LocalDate requiredDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestPriority priority;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    protected SupplyRequest() {}

    public SupplyRequest(CreateSupplyRequestCommand command) {
        this.productId = command.productId();
        this.supplierId = command.supplierId();
        this.minimarketId = command.minimarketId();
        this.quantity = command.quantity();
        this.reason = command.reason();
        this.requiredDate = command.requiredDate();
        this.priority = command.priority();
        this.status = RequestStatus.PENDING;
    }
}
