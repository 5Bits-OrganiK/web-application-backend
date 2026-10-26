package _5bits.organik.platform.requisition.domain.model.commands;

import _5bits.organik.platform.requisition.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.requisition.domain.model.valueobjects.RequestPriority;
import _5bits.organik.platform.requisition.domain.model.valueobjects.RequestReason;
import _5bits.organik.platform.requisition.domain.model.valueobjects.SupplierId;

import java.time.LocalDate;

public record CreateSupplyRequestCommand(ProductId productId, SupplierId supplierId, MinimarketId minimarketId, Quantity quantity, RequestReason reason, LocalDate requiredDate, RequestPriority priority) {
    public CreateSupplyRequestCommand {
        if (productId == null) {
            throw new IllegalArgumentException("ProductId cannot be null");
        }
        if (supplierId == null) {
            throw new IllegalArgumentException("SupplierId cannot be null");
        }
        if (minimarketId == null) {
            throw new IllegalArgumentException("MinimarketId cannot be null");
        }
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (reason == null) {
            throw new IllegalArgumentException("RequestReason cannot be null");
        }
        if (requiredDate == null) {
            throw new IllegalArgumentException("RequiredDate cannot be null");
        }
        if (priority == null) {
            throw new IllegalArgumentException("RequestPriority cannot be null");
        }
    }
}
