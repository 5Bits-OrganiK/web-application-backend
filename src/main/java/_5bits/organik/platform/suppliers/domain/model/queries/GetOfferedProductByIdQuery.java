package _5bits.organik.platform.suppliers.domain.model.queries;

public record GetOfferedProductByIdQuery(Long id) {
    public GetOfferedProductByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("offeredProduct.error.id.invalid");
    }
}
