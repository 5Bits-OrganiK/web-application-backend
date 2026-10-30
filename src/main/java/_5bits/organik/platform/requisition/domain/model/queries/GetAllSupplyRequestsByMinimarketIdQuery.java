package _5bits.organik.platform.requisition.domain.model.queries;

import _5bits.organik.platform.requisition.domain.model.valueobjects.MinimarketId;

public record GetAllSupplyRequestsByMinimarketIdQuery(MinimarketId minimarketId) {
    public GetAllSupplyRequestsByMinimarketIdQuery {
        if (minimarketId == null) {
            throw new IllegalArgumentException("MinimarketId cannot be null");
        }
    }
}
