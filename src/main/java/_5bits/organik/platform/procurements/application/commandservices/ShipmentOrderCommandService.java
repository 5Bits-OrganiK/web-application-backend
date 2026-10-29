package _5bits.organik.platform.procurements.application.commandservices;

import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.commands.CreateShipmentOrderCommand;

public interface ShipmentOrderCommandService {
    ShipmentOrder handle(CreateShipmentOrderCommand command);
}
x