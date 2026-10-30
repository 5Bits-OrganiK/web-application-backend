package _5bits.organik.platform.suppliers.application.queryservices;

import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.domain.model.queries.GetAllOfferedProductsBySupplierIdQuery;
import _5bits.organik.platform.suppliers.domain.model.queries.GetOfferedProductByIdQuery;

import java.util.List;
import java.util.Optional;

public interface OfferedProductQueryService {
    List<OfferedProduct> handle(GetAllOfferedProductsBySupplierIdQuery query);
    Optional<OfferedProduct> handle(GetOfferedProductByIdQuery query);
}
