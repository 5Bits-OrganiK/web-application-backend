package _5bits.organik.platform.inventory.domain.model.aggregates;

import _5bits.organik.platform.inventory.domain.model.valueobjects.ExpirationDate;
import _5bits.organik.platform.inventory.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.inventory.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.inventory.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.ExpirationDateAttributeConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.LotCodeAttributeConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.MinimarketIdAttributeConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.ProductIdAttributeConverter;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.converters.QuantityAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "minimarket_id", "lot_code" }, name = StockLot.LOT_CODE_UNIQUE_CONSTRAINT)
})
public class StockLot {
    public static final String LOT_CODE_UNIQUE_CONSTRAINT = "uk_minimarket_lot_code";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = MinimarketIdAttributeConverter.class)
    private MinimarketId minimarketId;

    @Column(nullable = false)
    @Convert(converter = ProductIdAttributeConverter.class)
    private ProductId productId;

    @Column(nullable = false, length = 32)
    @Convert(converter = LotCodeAttributeConverter.class)
    private LotCode lotCode;

    @Column(nullable = false)
    @Convert(converter = QuantityAttributeConverter.class)
    private Quantity quantity;

    @Column(nullable = false)
    @Convert(converter = QuantityAttributeConverter.class)
    private Quantity minimumStock;

    @Column(nullable = false)
    @Convert(converter = ExpirationDateAttributeConverter.class)
    private ExpirationDate expirationDate;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    protected StockLot() {}

    public boolean isLowStock() {
        return quantity.isLessThanOrEqualTo(minimumStock);
    }
}
