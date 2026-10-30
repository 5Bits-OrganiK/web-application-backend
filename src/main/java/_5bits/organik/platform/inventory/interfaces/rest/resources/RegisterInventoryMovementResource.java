package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Request resource used to register an inventory movement on a stock lot")
public record RegisterInventoryMovementResource(
        @Schema(
                description = "Stock lot affected by the movement",
                example = "1",
                minimum = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{inventory.error.stockLotId.notNull}")
        @Positive(message = "{inventory.error.stockLotId.min}")
        Long stockLotId,

        @Schema(
                description = "Type of movement",
                example = "WASTE",
                allowableValues = {"ENTRY", "EXIT", "ADJUSTMENT", "WASTE"},
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{inventory.error.movementType.notBlank}")
        @Pattern(regexp = "^(ENTRY|EXIT|ADJUSTMENT|WASTE)$", message = "{inventory.error.movementType.pattern}")
        String type,

        @Schema(
                description = "Units moved",
                example = "3",
                minimum = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{inventory.error.quantity.notNull}")
        @Positive(message = "{inventory.error.quantity.positive}")
        Integer quantity,

        @Schema(
                description = "Reason for the movement, required for ADJUSTMENT and WASTE",
                example = "Damaged during display",
                maxLength = 256)
        @Size(max = 256, message = "{inventory.error.movementReason.size}")
        String reason) {

    @Schema(hidden = true)
    @AssertTrue(message = "{inventory.error.movementReason.required}")
    public boolean isReasonProvidedWhenRequired() {
        boolean requiresReason = "ADJUSTMENT".equals(type) || "WASTE".equals(type);
        return !requiresReason || (reason != null && !reason.isBlank());
    }
}
