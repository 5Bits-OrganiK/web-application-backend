package _5bits.organik.platform.inventory.domain.model.queries;

import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;

public record GetAllOffersByStockLotIdQuery(StockLotId stockLotId) {
    public GetAllOffersByStockLotIdQuery {
        if (stockLotId == null) throw new IllegalArgumentException("stockLotId cannot be null");
    }
}
