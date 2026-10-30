package _5bits.organik.platform.inventory.application.commandservices;

public sealed interface OfferCommandFailure
        permits OfferCommandFailure.StockLotNotFound, OfferCommandFailure.EndsAfterExpiration {
    String messageKey();

    record StockLotNotFound() implements OfferCommandFailure {
        @Override
        public String messageKey() {
            return "offer.error.stockLotNotFound";
        }
    }

    record EndsAfterExpiration() implements OfferCommandFailure {
        @Override
        public String messageKey() {
            return "offer.error.endsAfterExpiration";
        }
    }
}
