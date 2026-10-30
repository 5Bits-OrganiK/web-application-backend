package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Request resource used to create an offer for a stock lot")
public record CreateOfferResource(
        @Schema(
                description = "Stock lot placed on offer",
                example = "1",
                minimum = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{inventory.error.stockLotId.notNull}")
        @Positive(message = "{inventory.error.stockLotId.min}")
        Long stockLotId,

        @Schema(
                description = "Promotional price per unit",
                example = "7.90",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{inventory.error.offerPrice.notNull}")
        @DecimalMin(value = "0.00", inclusive = false, message = "{inventory.error.offerPrice.positive}")
        @Digits(integer = 8, fraction = 2, message = "{inventory.error.offerPrice.scale}")
        BigDecimal price,

        @Schema(
                description = "First day of the offer",
                example = "2026-11-01",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{offer.error.startsOn.notNull}")
        LocalDate startsOn,

        @Schema(
                description = "Last day of the offer, not after the lot expiration date",
                example = "2026-11-10",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{offer.error.endsOn.notNull}")
        LocalDate endsOn) {

    @Schema(hidden = true)
    @AssertTrue(message = "{offer.error.period.invalid}")
    public boolean isPeriodValid() {
        return startsOn == null || endsOn == null || !endsOn.isBefore(startsOn);
    }
}
