package _5bits.organik.platform.inventory.application.commandservices;

import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.domain.model.commands.RegisterInventoryMovementCommand;
import _5bits.organik.platform.shared.application.result.Result;

public interface InventoryMovementCommandService {
    Result<InventoryMovement, InventoryMovementCommandFailure> handle(RegisterInventoryMovementCommand command);
}
