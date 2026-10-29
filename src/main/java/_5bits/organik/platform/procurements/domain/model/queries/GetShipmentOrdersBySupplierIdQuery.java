package _5bits.organik.platform.procurements.domain.model.queries;

import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;

public record GetShipmentOrdersBySupplierIdQuery(SupplierId supplierId) {
    public GetShipmentOrdersBySupplierIdQuery {
        if (supplierId == null) {
            throw new IllegalArgumentException("SupplierId cannot be null");
        }
    }
}
