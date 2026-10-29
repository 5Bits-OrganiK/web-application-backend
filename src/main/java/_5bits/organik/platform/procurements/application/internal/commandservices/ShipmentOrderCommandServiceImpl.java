package _5bits.organik.platform.procurements.application.internal.commandservices;

import _5bits.organik.platform.procurements.application.commandservices.ShipmentOrderCommandService;
import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.commands.CreateShipmentOrderCommand;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.ShipmentOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShipmentOrderCommandServiceImpl implements ShipmentOrderCommandService {
    private final ShipmentOrderRepository shipmentOrderRepository;

    public ShipmentOrderCommandServiceImpl(ShipmentOrderRepository shipmentOrderRepository) {
        this.shipmentOrderRepository = shipmentOrderRepository;
    }

    @Override
    @Transactional
    public ShipmentOrder handle(CreateShipmentOrderCommand command) {
        return shipmentOrderRepository.save(new ShipmentOrder(command));
    }
}
x