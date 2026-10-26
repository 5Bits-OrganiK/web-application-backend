package _5bits.organik.platform.procurements.domain.model.entities;

import _5bits.organik.platform.procurements.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.procurements.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.LotCodeAttributeConverter;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.ProductIdAttributeConverter;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.converters.QuantityAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class ShipmentOrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = ProductIdAttributeConverter.class)
    private ProductId productId;

    @Column(nullable = false)
    @Convert(converter = QuantityAttributeConverter.class)
    private Quantity quantity;

    @Column(nullable = false)
    @Convert(converter = LotCodeAttributeConverter.class)
    private LotCode lotCode;

    @Column(nullable = false)
    private LocalDate expiresOn;
}
