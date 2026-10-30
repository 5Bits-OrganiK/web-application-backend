package _5bits.organik.platform.inventory.application.internal.queryservices;

import _5bits.organik.platform.inventory.application.queryservices.InventoryMovementQueryService;
import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllInventoryMovementsByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetInventoryMovementByIdQuery;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.InventoryMovementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class InventoryMovementQueryServiceImpl implements InventoryMovementQueryService {
    private final InventoryMovementRepository inventoryMovementRepository;

    public InventoryMovementQueryServiceImpl(InventoryMovementRepository inventoryMovementRepository) {
        this.inventoryMovementRepository = inventoryMovementRepository;
    }

    @Override
    public List<InventoryMovement> handle(GetAllInventoryMovementsByStockLotIdQuery query) {
        log.debug("Querying all inventory movements for stockLotId={}", query.stockLotId().value());
        var results = inventoryMovementRepository.findAllByStockLotIdOrderByCreatedAtDesc(query.stockLotId());
        log.debug("Found {} inventory movement(s) for stockLotId={}", results.size(), query.stockLotId().value());
        return results;
    }

    @Override
    public Optional<InventoryMovement> handle(GetInventoryMovementByIdQuery query) {
        log.debug("Querying inventory movement by id={}", query.id());
        var result = inventoryMovementRepository.findById(query.id());
        if (result.isEmpty()) log.debug("No inventory movement found for id={}", query.id());
        return result;
    }
}
