package _5bits.organik.platform.suppliers.domain.model.queries;

import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;

public record GetAllOfferedProductsBySupplierIdQuery(SupplierId supplierId) {
    public GetAllOfferedProductsBySupplierIdQuery {
        if (supplierId == null) {
            throw new IllegalArgumentException("SupplierId cannot be null");
        }
    }
}
