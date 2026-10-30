package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.domain.model.commands.CreateStockLotCommand;
import _5bits.organik.platform.inventory.domain.model.valueobjects.ExpirationDate;
import _5bits.organik.platform.inventory.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.inventory.interfaces.rest.resources.CreateStockLotResource;

public class CreateStockLotCommandFromResourceAssembler {
    public static CreateStockLotCommand toCommandFromResource(CreateStockLotResource resource) {
        return new CreateStockLotCommand(
                InventoryValueObjectFromStringAssembler.toMinimarketIdFromString(resource.minimarketId()),
                InventoryValueObjectFromStringAssembler.toProductIdFromString(resource.productId()),
                InventoryValueObjectFromStringAssembler.toLotCodeFromString(resource.lotCode()),
                new Quantity(resource.quantity()),
                new Quantity(resource.minimumStock()),
                new ExpirationDate(resource.expirationDate()));
    }
}
