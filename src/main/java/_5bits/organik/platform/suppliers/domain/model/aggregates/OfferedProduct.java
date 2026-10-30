package _5bits.organik.platform.suppliers.domain.model.aggregates;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.LotCodeAttributeConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.ProductIdAttributeConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.QuantityAttributeConverter;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.converters.SupplierIdAttributeConverter;
import _5bits.organik.platform.suppliers.domain.model.commands.CreateOfferedProductCommand;
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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "supplier_id", "lot_code" }, name = OfferedProduct.SUPPLIER_LOT_UNIQUE_CONSTRAINT)
})
public class OfferedProduct {
    public static final String SUPPLIER_LOT_UNIQUE_CONSTRAINT = "uk_offered_product_supplier_lot";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = SupplierIdAttributeConverter.class)
    private SupplierId supplierId;

    @Column(nullable = false)
    @Convert(converter = ProductIdAttributeConverter.class)
    private ProductId productId;

    @Column(nullable = false)
    @Convert(converter = LotCodeAttributeConverter.class)
    private LotCode lotCode;

    @Column(nullable = false)
    @Convert(converter = QuantityAttributeConverter.class)
    private Quantity availableQuantity;

    @Column(nullable = false)
    private LocalDate expiresOn;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    protected OfferedProduct() {}

    public OfferedProduct(CreateOfferedProductCommand command) {
        this.supplierId = command.supplierId();
        this.productId = command.productId();
        this.lotCode = command.lotCode();
        this.availableQuantity = command.availableQuantity();
        this.expiresOn = command.expiresOn();
    }
}
