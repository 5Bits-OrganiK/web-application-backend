package _5bits.organik.platform.inventory.application.commandservices;

import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.domain.model.commands.CreateOfferCommand;
import _5bits.organik.platform.shared.application.result.Result;

public interface OfferCommandService {
    Result<Offer, OfferCommandFailure> handle(CreateOfferCommand command);
}
