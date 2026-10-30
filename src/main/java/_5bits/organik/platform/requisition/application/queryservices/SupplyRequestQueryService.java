package _5bits.organik.platform.requisition.application.queryservices;

import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsByMinimarketIdQuery;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsBySupplierIdQuery;
import _5bits.organik.platform.requisition.domain.model.queries.GetSupplyRequestByIdQuery;

import java.util.List;
import java.util.Optional;

public interface SupplyRequestQueryService {
    Optional<SupplyRequest> handle(GetSupplyRequestByIdQuery query);
    List<SupplyRequest> handle(GetAllSupplyRequestsByMinimarketIdQuery query);
    List<SupplyRequest> handle(GetAllSupplyRequestsBySupplierIdQuery query);
}
