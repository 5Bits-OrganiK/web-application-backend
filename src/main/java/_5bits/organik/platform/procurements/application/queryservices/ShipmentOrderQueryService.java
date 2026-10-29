package _5bits.organik.platform.procurements.application.queryservices;

import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrderByIdQuery;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrdersByMinimarketIdQuery;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrdersBySupplierIdQuery;

import java.util.List;
import java.util.Optional;

public interface ShipmentOrderQueryService {
    Optional<ShipmentOrder> handle(GetShipmentOrderByIdQuery query);
    List<ShipmentOrder> handle(GetShipmentOrdersBySupplierIdQuery query);
    List<ShipmentOrder> handle(GetShipmentOrdersByMinimarketIdQuery query);
}
x