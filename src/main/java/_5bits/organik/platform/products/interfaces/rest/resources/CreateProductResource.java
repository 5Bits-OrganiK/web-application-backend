package _5bits.organik.platform.products.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Request resource used to create a product")
public record CreateProductResource(
        @Schema(
                description = "Stock keeping unit that identifies the product",
                example = "ORG-01",
                maxLength = 32,
                pattern = "^[A-Z0-9-]+$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{product.error.sku.notBlank}")
        @Size(max = 32, message = "{product.error.sku.size}")
        @Pattern(regexp = "^[A-Z0-9-]+$", message = "{product.error.sku.pattern}")
        String sku,

        @Schema(
                description = "Name of the product",
                example = "Organic apple",
                maxLength = 256,
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{product.error.name.notBlank}")
        @Size(max = 256, message = "{product.error.name.size}")
        String name,

        @Schema(
                description = "Identifier of the category the product belongs to",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{product.error.categoryId.notNull}")
        @Positive(message = "{product.error.categoryId.invalid}")
        Long categoryId,

        @Schema(
                description = "Supplier that usually provides the product",
                example = "sup-bioandes",
                maxLength = 256,
                pattern = "^[a-zA-Z0-9_-]+$")
        @Size(max = 256, message = "{product.error.supplierId.size}")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{product.error.supplierId.pattern}")
        String supplierId,

        @Schema(
                description = "Unit in which the stock of the product is counted",
                example = "KG",
                allowableValues = {"UNIT", "KG", "LITER", "PACK"},
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{product.error.unit.notBlank}")
        @Pattern(regexp = "^(UNIT|KG|LITER|PACK)$", message = "{product.error.unit.pattern}")
        String unit,

        @Schema(
                description = "Stock under which the product needs to be replenished",
                example = "10",
                minimum = "0",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{product.error.minimumStock.notNull}")
        @Min(value = 0, message = "{product.error.minimumStock.min}")
        Integer minimumStock) { }
