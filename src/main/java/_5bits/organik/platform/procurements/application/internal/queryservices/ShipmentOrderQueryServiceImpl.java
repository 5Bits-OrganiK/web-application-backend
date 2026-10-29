package _5bits.organik.platform.procurements.application.internal.queryservices;

import _5bits.organik.platform.procurements.application.queryservices.ShipmentOrderQueryService;
import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrderByIdQuery;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrdersByMinimarketIdQuery;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrdersBySupplierIdQuery;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.ShipmentOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ShipmentOrderQueryServiceImpl implements ShipmentOrderQueryService {
    private final ShipmentOrderRepository shipmentOrderRepository;

    public ShipmentOrderQueryServiceImpl(ShipmentOrderRepository shipmentOrderRepository) {
        this.shipmentOrderRepository = shipmentOrderRepository;
    }

    @Override
    public Optional<ShipmentOrder> handle(GetShipmentOrderByIdQuery query) {
        return shipmentOrderRepository.findById(query.id());
    }

    @Override
    public List<ShipmentOrder> handle(GetShipmentOrdersBySupplierIdQuery query) {
        return shipmentOrderRepository.findAllBySupplierId(query.supplierId());
    }

    @Override
    public List<ShipmentOrder> handle(GetShipmentOrdersByMinimarketIdQuery query) {
        return shipmentOrderRepository.findAllByMinimarketId(query.minimarketId());
    }
}
