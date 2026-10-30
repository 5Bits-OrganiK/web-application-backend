package _5bits.organik.platform.suppliers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Response resource representing a supplier")
public record SupplierResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,
        @Schema(description = "Business name of the supplier", example = "Bioandes S.A.C.")
        String businessName,
        @Schema(description = "Contact person of the supplier", example = "Enrique Villar")
        String contactName,
        @Schema(description = "Contact phone number", example = "+51 987 654 321")
        String phoneNumber,
        @Schema(description = "Contact email of the supplier", example = "ventas@bioandes.pe")
        String email,
        @Schema(description = "Product categories the supplier offers", example = "[\"GRAINS\"]")
        List<String> categories,
        @Schema(description = "Organic certification code", example = "PE-BIO-140")
        String organicCertification) {
}
