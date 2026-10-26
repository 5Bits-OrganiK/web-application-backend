package _5bits.organik.platform.procurements.domain.model.aggregates;

import _5bits.organik.platform.procurements.domain.model.entities.ShipmentOrderLine;
import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.OrderStatus;
import _5bits.organik.platform.procurements.domain.model.valueobjects.RejectionReason;
import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.UserId;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.MinimarketIdAttributeConverter;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.RejectionReasonAttributeConverter;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.SupplierIdAttributeConverter;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.UserIdAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ShipmentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = SupplierIdAttributeConverter.class)
    private SupplierId supplierId;

    @Column(nullable = false)
    @Convert(converter = MinimarketIdAttributeConverter.class)
    private MinimarketId minimarketId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "shipment_order_id", nullable = false)
    private List<ShipmentOrderLine> lines = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Convert(converter = UserIdAttributeConverter.class)
    private UserId decidedBy;

    private Instant decidedAt;

    @Convert(converter = RejectionReasonAttributeConverter.class)
    private RejectionReason rejectionReason;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;
}
