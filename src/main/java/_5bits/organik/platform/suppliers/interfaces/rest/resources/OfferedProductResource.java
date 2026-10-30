package _5bits.organik.platform.suppliers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Response resource representing a product offered by a supplier")
public record OfferedProductResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,
        @Schema(description = "Supplier that offers the product", example = "1")
        String supplierId,
        @Schema(description = "Product of the catalog", example = "prod-quinua-blanca")
        String productId,
        @Schema(description = "Lot code of the offered product", example = "BA-0412")
        String lotCode,
        @Schema(description = "Available quantity of the lot", example = "120")
        Integer availableQuantity,
        @Schema(description = "Expiration date of the lot", example = "2026-12-15")
        LocalDate expiresOn) {
}
