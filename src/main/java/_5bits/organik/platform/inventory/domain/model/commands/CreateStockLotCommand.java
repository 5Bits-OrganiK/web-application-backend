package _5bits.organik.platform.inventory.domain.model.commands;

import _5bits.organik.platform.inventory.domain.model.valueobjects.ExpirationDate;
import _5bits.organik.platform.inventory.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.inventory.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.inventory.domain.model.valueobjects.Quantity;

public record CreateStockLotCommand(MinimarketId minimarketId, ProductId productId, LotCode lotCode,
                                    Quantity quantity, Quantity minimumStock, ExpirationDate expirationDate) {
    public CreateStockLotCommand {
        if (minimarketId == null) {
            throw new IllegalArgumentException("MinimarketId cannot be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("ProductId cannot be null");
        }
        if (lotCode == null) {
            throw new IllegalArgumentException("LotCode cannot be null");
        }
        if (quantity == null || !quantity.isPositive()) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (minimumStock == null) {
            throw new IllegalArgumentException("Minimum stock cannot be null");
        }
        if (expirationDate == null) {
            throw new IllegalArgumentException("ExpirationDate cannot be null");
        }
    }
}
