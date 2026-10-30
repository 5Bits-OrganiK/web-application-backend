package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Response resource representing a stock lot")
public record StockLotResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,
        @Schema(description = "Minimarket that owns the stock lot", example = "mm-vida-verde")
        String minimarketId,
        @Schema(description = "Product stored in the lot", example = "prd-quinoa-blanca")
        String productId,
        @Schema(description = "Lot code", example = "LOT-2026-0412")
        String lotCode,
        @Schema(description = "Units currently in the lot", example = "40")
        Integer quantity,
        @Schema(description = "Minimum stock threshold", example = "10")
        Integer minimumStock,
        @Schema(description = "Date until which the lot can be sold", example = "2026-12-15")
        LocalDate expirationDate,
        @Schema(description = "Whether the lot is at or below its minimum stock", example = "false")
        boolean lowStock) {
}
