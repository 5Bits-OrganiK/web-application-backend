package _5bits.organik.platform.procurements.domain.model.commands;

import _5bits.organik.platform.procurements.domain.model.valueobjects.UserId;

public record AcceptShipmentOrderCommand(Long shipmentOrderId, UserId decidedBy) {
    public AcceptShipmentOrderCommand {
        if (shipmentOrderId == null || shipmentOrderId <= 0) {
            throw new IllegalArgumentException("ShipmentOrderId must be greater than zero");
        }
        if (decidedBy == null) {
            throw new IllegalArgumentException("DecidedBy cannot be null");
        }
    }
}
