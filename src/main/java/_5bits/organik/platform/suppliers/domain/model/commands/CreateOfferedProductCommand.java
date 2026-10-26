package _5bits.organik.platform.suppliers.domain.model.commands;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;

import java.time.LocalDate;

public record CreateOfferedProductCommand(SupplierId supplierId, ProductId productId, LotCode lotCode, Quantity availableQuantity, LocalDate expiresOn) {
    public CreateOfferedProductCommand {
        if (supplierId == null) {
            throw new IllegalArgumentException("SupplierId cannot be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("ProductId cannot be null");
        }
        if (lotCode == null) {
            throw new IllegalArgumentException("LotCode cannot be null");
        }
        if (availableQuantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (expiresOn == null) {
            throw new IllegalArgumentException("ExpiresOn cannot be null");
        }
    }
}
