package _5bits.organik.platform.procurements.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AcceptShipmentOrderResource(
        @NotNull(message = "{shipmentOrder.error.decidedBy.notNull}")
        @Min(value = 1, message = "{shipmentOrder.error.decidedBy.min}")
        Long decidedBy) {}
