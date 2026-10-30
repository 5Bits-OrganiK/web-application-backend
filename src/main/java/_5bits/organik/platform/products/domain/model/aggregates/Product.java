package _5bits.organik.platform.products.domain.model.aggregates;

import _5bits.organik.platform.products.domain.model.commands.CreateProductCommand;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryId;
import _5bits.organik.platform.products.domain.model.valueobjects.MeasurementUnit;
import _5bits.organik.platform.products.domain.model.valueobjects.MinimumStock;
import _5bits.organik.platform.products.domain.model.valueobjects.ProductName;
import _5bits.organik.platform.products.domain.model.valueobjects.Sku;
import _5bits.organik.platform.products.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.converters.CategoryIdAttributeConverter;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.converters.MinimumStockAttributeConverter;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.converters.ProductNameAttributeConverter;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.converters.SkuAttributeConverter;
import _5bits.organik.platform.products.infrastructure.persistence.jpa.converters.SupplierIdAttributeConverter;
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
        @UniqueConstraint(columnNames = { "sku" }, name = Product.SKU_UNIQUE_CONSTRAINT)
})
public class Product {
    public static final String SKU_UNIQUE_CONSTRAINT = "uk_product_sku";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    @Convert(converter = SkuAttributeConverter.class)
    private Sku sku;

    @Column(nullable = false)
    @Convert(converter = ProductNameAttributeConverter.class)
    private ProductName name;

    @Column(nullable = false)
    @Convert(converter = CategoryIdAttributeConverter.class)
    private CategoryId categoryId;

    @Convert(converter = SupplierIdAttributeConverter.class)
    private SupplierId supplierId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MeasurementUnit unit;

    @Column(nullable = false)
    @Convert(converter = MinimumStockAttributeConverter.class)
    private MinimumStock minimumStock;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    protected Product() {}

    public Product(CreateProductCommand command) {
        this.sku = command.sku();
        this.name = command.name();
        this.categoryId = command.categoryId();
        this.supplierId = command.supplierId();
        this.unit = command.unit();
        this.minimumStock = command.minimumStock();
    }
}
