package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.domain.model.commands.CreateOfferCommand;
import _5bits.organik.platform.inventory.domain.model.valueobjects.OfferPrice;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.interfaces.rest.resources.CreateOfferResource;

public class CreateOfferCommandFromResourceAssembler {
    public static CreateOfferCommand toCommandFromResource(CreateOfferResource resource) {
        return new CreateOfferCommand(
                new StockLotId(resource.stockLotId()),
                new OfferPrice(resource.price()),
                resource.startsOn(),
                resource.endsOn());
    }
}
