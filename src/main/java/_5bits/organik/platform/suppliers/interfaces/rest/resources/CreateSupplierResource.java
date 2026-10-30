package _5bits.organik.platform.suppliers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Request resource used to create a supplier")
public record CreateSupplierResource(
        @Schema(description = "Business name of the supplier", example = "Bioandes S.A.C.", maxLength = 256,
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{supplier.error.businessName.notBlank}")
        @Size(max = 256, message = "{supplier.error.businessName.size}")
        String businessName,

        @Schema(description = "Contact person of the supplier", example = "Enrique Villar", maxLength = 256,
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{supplier.error.contactName.notBlank}")
        @Size(max = 256, message = "{supplier.error.contactName.size}")
        String contactName,

        @Schema(description = "Contact phone number", example = "+51 987 654 321", maxLength = 20,
                pattern = "^\\+?[0-9][0-9 -]{5,19}$", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{supplier.error.phoneNumber.notBlank}")
        @Size(max = 20, message = "{supplier.error.phoneNumber.size}")
        @Pattern(regexp = "^\\+?[0-9][0-9 -]{5,19}$", message = "{supplier.error.phoneNumber.pattern}")
        String phoneNumber,

        @Schema(description = "Contact email of the supplier", example = "ventas@bioandes.pe", maxLength = 256,
                pattern = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{supplier.error.email.notBlank}")
        @Size(max = 256, message = "{supplier.error.email.size}")
        @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$", message = "{supplier.error.email.pattern}")
        String email,

        @Schema(description = "Product categories the supplier offers",
                example = "[\"FRUITS_AND_VEGETABLES\", \"GRAINS\"]", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "{supplier.error.categories.notEmpty}")
        List<@NotBlank @Pattern(regexp = "^(FRUITS_AND_VEGETABLES|GRAINS|DAIRY|FROZEN)$", message = "{supplier.error.categories.pattern}") String> categories,

        @Schema(description = "Organic certification code", example = "PE-BIO-140", maxLength = 64,
                pattern = "^[a-zA-Z0-9 .-]+$", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{supplier.error.organicCertification.notBlank}")
        @Size(max = 64, message = "{supplier.error.organicCertification.size}")
        @Pattern(regexp = "^[a-zA-Z0-9 .-]+$", message = "{supplier.error.organicCertification.pattern}")
        String organicCertification) { }
