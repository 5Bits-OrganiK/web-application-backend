package _5bits.organik.platform.inventory.application.internal.queryservices;

import _5bits.organik.platform.inventory.application.queryservices.StockLotQueryService;
import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllStockLotsByMinimarketIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetStockLotByIdQuery;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.StockLotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class StockLotQueryServiceImpl implements StockLotQueryService {
    private final StockLotRepository stockLotRepository;

    public StockLotQueryServiceImpl(StockLotRepository stockLotRepository) {
        this.stockLotRepository = stockLotRepository;
    }

    @Override
    public List<StockLot> handle(GetAllStockLotsByMinimarketIdQuery query) {
        log.debug("Querying all stock lots for minimarketId={}", query.minimarketId().value());
        var results = stockLotRepository.findAllByMinimarketId(query.minimarketId());
        log.debug("Found {} stock lot(s) for minimarketId={}", results.size(), query.minimarketId().value());
        return results;
    }

    @Override
    public Optional<StockLot> handle(GetStockLotByIdQuery query) {
        log.debug("Querying stock lot by id={}", query.id());
        var result = stockLotRepository.findById(query.id());
        if (result.isEmpty()) log.debug("No stock lot found for id={}", query.id());
        return result;
    }
}
