package _5bits.organik.platform.suppliers.domain.model.queries;

public record GetSupplierByIdQuery(Long id) {
    public GetSupplierByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("supplier.error.id.invalid");
    }
}
