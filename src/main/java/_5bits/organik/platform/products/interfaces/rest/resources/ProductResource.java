package _5bits.organik.platform.products.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response resource representing a product")
public record ProductResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,
        @Schema(description = "Stock keeping unit of the product", example = "ORG-01")
        String sku,
        @Schema(description = "Name of the product", example = "Organic apple")
        String name,
        @Schema(description = "Identifier of the category", example = "1")
        Long categoryId,
        @Schema(description = "Supplier that usually provides the product", example = "sup-bioandes")
        String supplierId,
        @Schema(description = "Unit in which the stock is counted", example = "KG")
        String unit,
        @Schema(description = "Stock under which the product needs to be replenished", example = "10")
        Integer minimumStock) {
}
