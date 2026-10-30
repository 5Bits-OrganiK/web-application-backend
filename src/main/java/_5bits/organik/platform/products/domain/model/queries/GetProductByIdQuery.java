package _5bits.organik.platform.products.domain.model.queries;

public record GetProductByIdQuery(Long id) {
    public GetProductByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("product.error.id.invalid");
    }
}
