package _5bits.organik.platform.procurements.application.commandservices;

import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.commands.AcceptShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.commands.CreateShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.commands.RejectShipmentOrderCommand;
import _5bits.organik.platform.shared.application.result.Result;

public interface ShipmentOrderCommandService {
    Result<ShipmentOrder, ShipmentOrderCommandFailure> handle(CreateShipmentOrderCommand command);
    Result<ShipmentOrder, ShipmentOrderCommandFailure> handle(AcceptShipmentOrderCommand command);
    Result<ShipmentOrder, ShipmentOrderCommandFailure> handle(RejectShipmentOrderCommand command);
}
