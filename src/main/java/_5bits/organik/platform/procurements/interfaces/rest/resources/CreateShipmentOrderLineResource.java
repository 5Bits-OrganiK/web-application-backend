package _5bits.organik.platform.procurements.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateShipmentOrderLineResource(
        @NotBlank(message = "{shipmentOrder.error.productId.notBlank}")
        @Size(max = 256, message = "{shipmentOrder.error.productId.size}")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{shipmentOrder.error.productId.pattern}")
        String productId,

        @NotNull(message = "{shipmentOrder.error.quantity.notNull}")
        @Min(value = 1, message = "{shipmentOrder.error.quantity.min}")
        Integer quantity,

        @NotBlank(message = "{shipmentOrder.error.lotCode.notBlank}")
        @Size(max = 32, message = "{shipmentOrder.error.lotCode.size}")
        @Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "{shipmentOrder.error.lotCode.pattern}")
        String lotCode,

        @NotNull(message = "{shipmentOrder.error.expiresOn.notNull}")
        LocalDate expiresOn) {}
