package _5bits.organik.platform.inventory.application.internal.commandservices;

import _5bits.organik.platform.inventory.application.commandservices.StockLotCommandFailure;
import _5bits.organik.platform.inventory.application.commandservices.StockLotCommandService;
import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.commands.CreateStockLotCommand;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.StockLotRepository;
import _5bits.organik.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static _5bits.organik.platform.inventory.domain.model.aggregates.StockLot.LOT_CODE_UNIQUE_CONSTRAINT;

@Slf4j
@Service
public class StockLotCommandServiceImpl implements StockLotCommandService {
    private final StockLotRepository stockLotRepository;

    public StockLotCommandServiceImpl(StockLotRepository stockLotRepository) {
        this.stockLotRepository = stockLotRepository;
    }

    @Override
    @Transactional
    public Result<StockLot, StockLotCommandFailure> handle(CreateStockLotCommand command) {
        if (stockLotRepository.existsByMinimarketIdAndLotCode(command.minimarketId(), command.lotCode())) {
            log.warn("Duplicate stock lot detected (pre-check): minimarketId={}, lotCode={}",
                    command.minimarketId().value(), command.lotCode().value());
            return duplicateResult();
        }
        try {
            var createdStockLot = stockLotRepository.save(new StockLot(command));
            log.info("Stock lot created: id={}, minimarketId={}, productId={}, lotCode={}, quantity={}",
                    createdStockLot.getId(),
                    command.minimarketId().value(),
                    command.productId().value(),
                    command.lotCode().value(),
                    command.quantity().value());
            return Result.success(createdStockLot);
        } catch (DataIntegrityViolationException exception) {
            if (isDuplicateStockLotViolation(exception)) {
                log.warn("Duplicate stock lot detected (constraint violation): minimarketId={}, lotCode={}",
                        command.minimarketId().value(), command.lotCode().value());
                return duplicateResult();
            }
            log.error("Unexpected data integrity violation saving stock lot: lotCode={}",
                    command.lotCode().value(), exception);
            throw exception;
        }
    }

    private Result<StockLot, StockLotCommandFailure> duplicateResult() {
        return Result.failure(new StockLotCommandFailure.Duplicate());
    }

    private boolean isDuplicateStockLotViolation(DataIntegrityViolationException exception) {
        Throwable violationCause = exception;
        while (violationCause != null) {
            String message = violationCause.getMessage();
            if (message != null && message.contains(LOT_CODE_UNIQUE_CONSTRAINT)) return true;
            violationCause = violationCause.getCause();
        }
        return false;
    }
}
