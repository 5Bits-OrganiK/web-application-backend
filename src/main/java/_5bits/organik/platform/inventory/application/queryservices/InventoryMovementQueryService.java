package _5bits.organik.platform.inventory.application.queryservices;

import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllInventoryMovementsByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetInventoryMovementByIdQuery;

import java.util.List;
import java.util.Optional;

public interface InventoryMovementQueryService {
    List<InventoryMovement> handle(GetAllInventoryMovementsByStockLotIdQuery query);
    Optional<InventoryMovement> handle(GetInventoryMovementByIdQuery query);
}
