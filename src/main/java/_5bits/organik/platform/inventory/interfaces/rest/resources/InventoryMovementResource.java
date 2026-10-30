package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response resource representing an inventory movement")
public record InventoryMovementResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,
        @Schema(description = "Stock lot affected by the movement", example = "1")
        Long stockLotId,
        @Schema(description = "Type of movement", example = "WASTE")
        String type,
        @Schema(description = "Units moved", example = "3")
        Integer quantity,
        @Schema(description = "Reason for the movement", example = "Damaged during display")
        String reason,
        @Schema(description = "Moment the movement was registered", example = "2026-10-30T15:20:00Z")
        Instant registeredAt) {
}
