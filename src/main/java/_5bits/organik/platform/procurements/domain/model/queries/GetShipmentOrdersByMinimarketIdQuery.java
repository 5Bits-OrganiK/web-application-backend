package _5bits.organik.platform.procurements.domain.model.queries;

import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;

public record GetShipmentOrdersByMinimarketIdQuery(MinimarketId minimarketId) {
    public GetShipmentOrdersByMinimarketIdQuery {
        if (minimarketId == null) {
            throw new IllegalArgumentException("MinimarketId cannot be null");
        }
    }
}
