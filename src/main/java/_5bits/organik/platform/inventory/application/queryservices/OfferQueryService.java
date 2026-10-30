package _5bits.organik.platform.inventory.application.queryservices;

import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllOffersByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetOfferByIdQuery;

import java.util.List;
import java.util.Optional;

public interface OfferQueryService {
    List<Offer> handle(GetAllOffersByStockLotIdQuery query);
    Optional<Offer> handle(GetOfferByIdQuery query);
}
