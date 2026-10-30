package _5bits.organik.platform.procurements.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RejectShipmentOrderResource(
        @NotNull(message = "{shipmentOrder.error.decidedBy.notNull}")
        @Min(value = 1, message = "{shipmentOrder.error.decidedBy.min}")
        Long decidedBy,

        @NotBlank(message = "{shipmentOrder.error.rejectionReason.notBlank}")
        @Size(max = 512, message = "{shipmentOrder.error.rejectionReason.size}")
        String reason) {}
