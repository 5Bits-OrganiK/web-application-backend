package _5bits.organik.platform.procurements.domain.model.commands;

import _5bits.organik.platform.procurements.domain.model.valueobjects.RejectionReason;
import _5bits.organik.platform.procurements.domain.model.valueobjects.UserId;

public record RejectShipmentOrderCommand(Long shipmentOrderId, UserId decidedBy, RejectionReason reason) {
    public RejectShipmentOrderCommand {
        if (shipmentOrderId == null || shipmentOrderId <= 0) {
            throw new IllegalArgumentException("ShipmentOrderId must be greater than zero");
        }
        if (decidedBy == null) {
            throw new IllegalArgumentException("DecidedBy cannot be null");
        }
        if (reason == null) {
            throw new IllegalArgumentException("RejectionReason cannot be null");
        }
    }
}
