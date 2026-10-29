package _5bits.organik.platform.procurements.interfaces.rest.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateShipmentOrderResource(
        @NotBlank(message = "{shipmentOrder.error.supplierId.notBlank}")
        @Size(max = 256, message = "{shipmentOrder.error.supplierId.size}")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{shipmentOrder.error.supplierId.pattern}")
        String supplierId,

        @NotBlank(message = "{shipmentOrder.error.minimarketId.notBlank}")
        @Size(max = 256, message = "{shipmentOrder.error.minimarketId.size}")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{shipmentOrder.error.minimarketId.pattern}")
        String minimarketId,

        @NotEmpty(message = "{shipmentOrder.error.lines.notEmpty}")
        List<@NotNull @Valid CreateShipmentOrderLineResource> lines) {}
x