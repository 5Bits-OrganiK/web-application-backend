package _5bits.organik.platform.inventory.application.internal.commandservices;

import _5bits.organik.platform.inventory.application.commandservices.InventoryMovementCommandFailure;
import _5bits.organik.platform.inventory.application.commandservices.InventoryMovementCommandService;
import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.domain.model.commands.RegisterInventoryMovementCommand;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementType;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.InventoryMovementRepository;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.StockLotRepository;
import _5bits.organik.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class InventoryMovementCommandServiceImpl implements InventoryMovementCommandService {
    private final InventoryMovementRepository inventoryMovementRepository;
    private final StockLotRepository stockLotRepository;

    public InventoryMovementCommandServiceImpl(InventoryMovementRepository inventoryMovementRepository,
                                               StockLotRepository stockLotRepository) {
        this.inventoryMovementRepository = inventoryMovementRepository;
        this.stockLotRepository = stockLotRepository;
    }

    @Override
    @Transactional
    public Result<InventoryMovement, InventoryMovementCommandFailure> handle(RegisterInventoryMovementCommand command) {
        var stockLot = stockLotRepository.findById(command.stockLotId().value()).orElse(null);
        if (stockLot == null) {
            log.warn("Inventory movement rejected, stock lot not found: stockLotId={}", command.stockLotId().value());
            return Result.failure(new InventoryMovementCommandFailure.StockLotNotFound());
        }
        if (decreasesStock(command.type()) && !command.quantity().isLessThanOrEqualTo(stockLot.getQuantity())) {
            log.warn("Inventory movement rejected, insufficient stock: stockLotId={}, requested={}, available={}",
                    command.stockLotId().value(), command.quantity().value(), stockLot.getQuantity().value());
            return Result.failure(new InventoryMovementCommandFailure.InsufficientStock());
        }
        var registeredMovement = inventoryMovementRepository.save(new InventoryMovement(command));
        log.info("Inventory movement registered: id={}, stockLotId={}, type={}, quantity={}",
                registeredMovement.getId(),
                command.stockLotId().value(),
                command.type(),
                command.quantity().value());
        return Result.success(registeredMovement);
    }

    private boolean decreasesStock(MovementType type) {
        return type == MovementType.EXIT || type == MovementType.WASTE;
    }
}
