package _5bits.organik.platform.iam.domain.model.queries;

import _5bits.organik.platform.iam.domain.model.valueobjects.MinimarketId;

public record GetAllUsersByMinimarketIdQuery(MinimarketId minimarketId) {
    public GetAllUsersByMinimarketIdQuery {
        if (minimarketId == null) throw new IllegalArgumentException("minimarketId cannot be null");
    }
}
