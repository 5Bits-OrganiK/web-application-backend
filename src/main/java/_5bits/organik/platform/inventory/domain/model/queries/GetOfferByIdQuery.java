package _5bits.organik.platform.inventory.domain.model.queries;

public record GetOfferByIdQuery(Long id) {
    public GetOfferByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("offer.error.id.invalid");
    }
}
