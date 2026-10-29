package _5bits.organik.platform.procurements.interfaces.rest.transform;

import _5bits.organik.platform.procurements.domain.model.commands.CreateShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.procurements.interfaces.rest.resources.CreateShipmentOrderResource;

public final class CreateShipmentOrderCommandFromResourceAssembler {
    private CreateShipmentOrderCommandFromResourceAssembler() {}

    public static CreateShipmentOrderCommand toCommandFromResource(CreateShipmentOrderResource resource) {
        var lines = resource.lines().stream()
                .map(line -> new CreateShipmentOrderCommand.Line(
                        new ProductId(line.productId()),
                        new Quantity(line.quantity()),
                        new LotCode(line.lotCode()),
                        line.expiresOn()))
                .toList();
        return new CreateShipmentOrderCommand(
                new SupplierId(resource.supplierId()),
                new MinimarketId(resource.minimarketId()),
                lines);
    }
}
x