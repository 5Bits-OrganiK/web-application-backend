package _5bits.organik.platform.inventory.application.internal.queryservices;

import _5bits.organik.platform.inventory.application.queryservices.OfferQueryService;
import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllOffersByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetOfferByIdQuery;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.OfferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class OfferQueryServiceImpl implements OfferQueryService {
    private final OfferRepository offerRepository;

    public OfferQueryServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Offer> handle(GetAllOffersByStockLotIdQuery query) {
        log.debug("Querying all offers for stockLotId={}", query.stockLotId().value());
        var results = offerRepository.findAllByStockLotIdOrderByStartsOnDesc(query.stockLotId());
        log.debug("Found {} offer(s) for stockLotId={}", results.size(), query.stockLotId().value());
        return results;
    }

    @Override
    public Optional<Offer> handle(GetOfferByIdQuery query) {
        log.debug("Querying offer by id={}", query.id());
        var result = offerRepository.findById(query.id());
        if (result.isEmpty()) log.debug("No offer found for id={}", query.id());
        return result;
    }
}
