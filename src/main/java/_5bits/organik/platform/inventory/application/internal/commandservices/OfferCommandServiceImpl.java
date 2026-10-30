package _5bits.organik.platform.inventory.application.internal.commandservices;

import _5bits.organik.platform.inventory.application.commandservices.OfferCommandFailure;
import _5bits.organik.platform.inventory.application.commandservices.OfferCommandService;
import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.domain.model.commands.CreateOfferCommand;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.OfferRepository;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.StockLotRepository;
import _5bits.organik.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OfferCommandServiceImpl implements OfferCommandService {
    private final OfferRepository offerRepository;
    private final StockLotRepository stockLotRepository;

    public OfferCommandServiceImpl(OfferRepository offerRepository, StockLotRepository stockLotRepository) {
        this.offerRepository = offerRepository;
        this.stockLotRepository = stockLotRepository;
    }

    @Override
    @Transactional
    public Result<Offer, OfferCommandFailure> handle(CreateOfferCommand command) {
        var stockLot = stockLotRepository.findById(command.stockLotId().value()).orElse(null);
        if (stockLot == null) {
            log.warn("Offer rejected, stock lot not found: stockLotId={}", command.stockLotId().value());
            return Result.failure(new OfferCommandFailure.StockLotNotFound());
        }
        if (command.endsOn().isAfter(stockLot.getExpirationDate().value())) {
            log.warn("Offer rejected, ends after lot expiration: stockLotId={}, endsOn={}, expirationDate={}",
                    command.stockLotId().value(), command.endsOn(), stockLot.getExpirationDate().value());
            return Result.failure(new OfferCommandFailure.EndsAfterExpiration());
        }
        var createdOffer = offerRepository.save(new Offer(command));
        log.info("Offer created: id={}, stockLotId={}, price={}, startsOn={}, endsOn={}",
                createdOffer.getId(),
                command.stockLotId().value(),
                command.price().value(),
                command.startsOn(),
                command.endsOn());
        return Result.success(createdOffer);
    }
}
