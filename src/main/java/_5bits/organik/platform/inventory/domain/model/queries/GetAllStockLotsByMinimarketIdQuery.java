package _5bits.organik.platform.inventory.domain.model.queries;

import _5bits.organik.platform.inventory.domain.model.valueobjects.MinimarketId;

public record GetAllStockLotsByMinimarketIdQuery(MinimarketId minimarketId) {
    public GetAllStockLotsByMinimarketIdQuery {
        if (minimarketId == null) throw new IllegalArgumentException("minimarketId cannot be null");
    }
}
