package _5bits.organik.platform.products.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response resource representing a product category")
public record CategoryResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,
        @Schema(description = "Name of the category", example = "Fruits and vegetables")
        String name,
        @Schema(description = "Accent color of the category", example = "GREEN")
        String tone) {
}
