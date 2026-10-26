package _5bits.organik.platform.procurements.domain.model.commands;

import _5bits.organik.platform.procurements.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;

import java.time.LocalDate;
import java.util.List;

public record CreateShipmentOrderCommand(SupplierId supplierId, MinimarketId minimarketId, List<Line> lines) {
    public CreateShipmentOrderCommand {
        if (supplierId == null) {
            throw new IllegalArgumentException("SupplierId cannot be null");
        }
        if (minimarketId == null) {
            throw new IllegalArgumentException("MinimarketId cannot be null");
        }
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Lines cannot be empty");
        }
    }

    public record Line(ProductId productId, Quantity quantity, LotCode lotCode, LocalDate expiresOn) {
        public Line {
            if (productId == null) {
                throw new IllegalArgumentException("ProductId cannot be null");
            }
            if (quantity == null) {
                throw new IllegalArgumentException("Quantity cannot be null");
            }
            if (lotCode == null) {
                throw new IllegalArgumentException("LotCode cannot be null");
            }
            if (expiresOn == null) {
                throw new IllegalArgumentException("ExpiresOn cannot be null");
            }
        }
    }
}
