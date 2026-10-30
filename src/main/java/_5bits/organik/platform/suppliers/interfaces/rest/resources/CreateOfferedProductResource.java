package _5bits.organik.platform.suppliers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Request resource used to register a product offered by a supplier")
public record CreateOfferedProductResource(
        @Schema(description = "Supplier that offers the product", example = "1", maxLength = 256,
                pattern = "^[a-zA-Z0-9_-]+$", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{offeredProduct.error.supplierId.notBlank}")
        @Size(max = 256, message = "{offeredProduct.error.supplierId.size}")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{offeredProduct.error.supplierId.pattern}")
        String supplierId,

        @Schema(description = "Product of the catalog", example = "prod-quinua-blanca", maxLength = 256,
                pattern = "^[a-zA-Z0-9_-]+$", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{offeredProduct.error.productId.notBlank}")
        @Size(max = 256, message = "{offeredProduct.error.productId.size}")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{offeredProduct.error.productId.pattern}")
        String productId,

        @Schema(description = "Lot code of the offered product", example = "BA-0412", maxLength = 32,
                pattern = "^[a-zA-Z0-9-]+$", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{offeredProduct.error.lotCode.notBlank}")
        @Size(max = 32, message = "{offeredProduct.error.lotCode.size}")
        @Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "{offeredProduct.error.lotCode.pattern}")
        String lotCode,

        @Schema(description = "Available quantity of the lot", example = "120", minimum = "0",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{offeredProduct.error.quantity.notNull}")
        @Min(value = 0, message = "{offeredProduct.error.quantity.min}")
        Integer availableQuantity,

        @Schema(description = "Expiration date of the lot", example = "2026-12-15",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{offeredProduct.error.expiresOn.notNull}")
        LocalDate expiresOn) { }
