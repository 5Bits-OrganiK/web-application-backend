package _5bits.organik.platform.inventory.application.queryservices;

import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllStockLotsByMinimarketIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetStockLotByIdQuery;

import java.util.List;
import java.util.Optional;

public interface StockLotQueryService {
    List<StockLot> handle(GetAllStockLotsByMinimarketIdQuery query);
    Optional<StockLot> handle(GetStockLotByIdQuery query);
}
