package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.domain.model.commands.RegisterInventoryMovementCommand;
import _5bits.organik.platform.inventory.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.interfaces.rest.resources.RegisterInventoryMovementResource;

public class RegisterInventoryMovementCommandFromResourceAssembler {
    public static RegisterInventoryMovementCommand toCommandFromResource(RegisterInventoryMovementResource resource) {
        return new RegisterInventoryMovementCommand(
                new StockLotId(resource.stockLotId()),
                InventoryValueObjectFromStringAssembler.toMovementTypeFromString(resource.type()),
                new Quantity(resource.quantity()),
                InventoryValueObjectFromStringAssembler.toMovementReasonFromString(resource.reason()));
    }
}
