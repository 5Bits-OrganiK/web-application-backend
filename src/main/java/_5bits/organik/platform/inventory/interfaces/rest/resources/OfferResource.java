package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Response resource representing an offer")
public record OfferResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,
        @Schema(description = "Stock lot placed on offer", example = "1")
        Long stockLotId,
        @Schema(description = "Promotional price per unit", example = "7.90")
        BigDecimal price,
        @Schema(description = "First day of the offer", example = "2026-11-01")
        LocalDate startsOn,
        @Schema(description = "Last day of the offer", example = "2026-11-10")
        LocalDate endsOn,
        @Schema(description = "Whether the offer is active today", example = "true")
        boolean active) {
}
