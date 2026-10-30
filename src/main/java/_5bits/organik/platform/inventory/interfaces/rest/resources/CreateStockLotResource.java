package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Request resource used to register a stock lot")
public record CreateStockLotResource(
        @Schema(
                description = "Minimarket that owns the stock lot",
                example = "mm-vida-verde",
                maxLength = 256,
                pattern = "^[a-zA-Z0-9_-]+$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{inventory.error.minimarketId.notBlank}")
        @Size(max = 256, message = "{inventory.error.minimarketId.size}")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{inventory.error.minimarketId.pattern}")
        String minimarketId,

        @Schema(
                description = "Product stored in the lot",
                example = "prd-quinoa-blanca",
                maxLength = 256,
                pattern = "^[a-zA-Z0-9_-]+$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{inventory.error.productId.notBlank}")
        @Size(max = 256, message = "{inventory.error.productId.size}")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{inventory.error.productId.pattern}")
        String productId,

        @Schema(
                description = "Lot code, unique per minimarket",
                example = "LOT-2026-0412",
                maxLength = 32,
                pattern = "^[a-zA-Z0-9-]+$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{inventory.error.lotCode.notBlank}")
        @Size(max = 32, message = "{inventory.error.lotCode.size}")
        @Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "{inventory.error.lotCode.pattern}")
        String lotCode,

        @Schema(
                description = "Units received in the lot",
                example = "40",
                minimum = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{inventory.error.quantity.notNull}")
        @Positive(message = "{inventory.error.quantity.positive}")
        Integer quantity,

        @Schema(
                description = "Threshold below which the lot is considered low on stock",
                example = "10",
                minimum = "0",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{inventory.error.minimumStock.notNull}")
        @PositiveOrZero(message = "{inventory.error.minimumStock.min}")
        Integer minimumStock,

        @Schema(
                description = "Date until which the lot can be sold",
                example = "2026-12-15",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{inventory.error.expirationDate.notNull}")
        LocalDate expirationDate) { }
