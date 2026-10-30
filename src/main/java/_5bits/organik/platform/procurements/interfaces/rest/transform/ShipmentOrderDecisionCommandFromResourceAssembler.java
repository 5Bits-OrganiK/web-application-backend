package _5bits.organik.platform.procurements.interfaces.rest.transform;

import _5bits.organik.platform.procurements.domain.model.commands.AcceptShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.commands.RejectShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.valueobjects.RejectionReason;
import _5bits.organik.platform.procurements.domain.model.valueobjects.UserId;
import _5bits.organik.platform.procurements.interfaces.rest.resources.AcceptShipmentOrderResource;
import _5bits.organik.platform.procurements.interfaces.rest.resources.RejectShipmentOrderResource;

public final class ShipmentOrderDecisionCommandFromResourceAssembler {
    private ShipmentOrderDecisionCommandFromResourceAssembler() {}

    public static AcceptShipmentOrderCommand toAcceptCommand(Long id, AcceptShipmentOrderResource resource) {
        return new AcceptShipmentOrderCommand(id, new UserId(resource.decidedBy()));
    }

    public static RejectShipmentOrderCommand toRejectCommand(Long id, RejectShipmentOrderResource resource) {
        return new RejectShipmentOrderCommand(id, new UserId(resource.decidedBy()), new RejectionReason(resource.reason()));
    }
}
