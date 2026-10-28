package _5bits.organik.platform.inventory.domain.model.commands;

import _5bits.organik.platform.inventory.domain.model.valueobjects.OfferPrice;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;

import java.time.LocalDate;

public record CreateOfferCommand(StockLotId stockLotId, OfferPrice price, LocalDate startsOn, LocalDate endsOn) {
    public CreateOfferCommand {
        if (stockLotId == null) {
            throw new IllegalArgumentException("StockLotId cannot be null");
        }
        if (price == null) {
            throw new IllegalArgumentException("OfferPrice cannot be null");
        }
        if (startsOn == null || endsOn == null) {
            throw new IllegalArgumentException("Offer start and end dates cannot be null");
        }
        if (endsOn.isBefore(startsOn)) {
            throw new IllegalArgumentException("Offer end date cannot be before its start date");
        }
    }
}
