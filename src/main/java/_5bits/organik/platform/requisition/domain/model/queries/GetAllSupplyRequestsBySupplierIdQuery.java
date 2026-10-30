package _5bits.organik.platform.requisition.domain.model.queries;

import _5bits.organik.platform.requisition.domain.model.valueobjects.SupplierId;

public record GetAllSupplyRequestsBySupplierIdQuery(SupplierId supplierId) {
    public GetAllSupplyRequestsBySupplierIdQuery {
        if (supplierId == null) {
            throw new IllegalArgumentException("SupplierId cannot be null");
        }
    }
}
