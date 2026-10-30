package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.interfaces.rest.resources.OfferResource;

import java.time.LocalDate;

public class OfferResourceFromEntityAssembler {
    public static OfferResource toResourceFromEntity(Offer entity) {
        return new OfferResource(
                entity.getId(),
                entity.getStockLotId().value(),
                entity.getPrice().value(),
                entity.getStartsOn(),
                entity.getEndsOn(),
                entity.isActiveOn(LocalDate.now()));
    }
}
