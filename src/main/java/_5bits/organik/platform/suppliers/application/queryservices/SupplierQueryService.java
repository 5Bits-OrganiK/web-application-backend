package _5bits.organik.platform.suppliers.application.queryservices;

import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.domain.model.queries.GetAllSuppliersQuery;
import _5bits.organik.platform.suppliers.domain.model.queries.GetSupplierByIdQuery;

import java.util.List;
import java.util.Optional;

public interface SupplierQueryService {
    List<Supplier> handle(GetAllSuppliersQuery query);
    Optional<Supplier> handle(GetSupplierByIdQuery query);
}
