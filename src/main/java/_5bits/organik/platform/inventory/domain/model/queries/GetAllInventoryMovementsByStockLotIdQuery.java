package _5bits.organik.platform.inventory.domain.model.queries;

import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;

public record GetAllInventoryMovementsByStockLotIdQuery(StockLotId stockLotId) {
    public GetAllInventoryMovementsByStockLotIdQuery {
        if (stockLotId == null) throw new IllegalArgumentException("stockLotId cannot be null");
    }
}
