package _5bits.organik.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response resource representing a user")
public record UserResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,
        @Schema(description = "Full name of the user", example = "Marco Quispe")
        String fullName,
        @Schema(description = "Email address of the user", example = "marco@bioandes.pe")
        String email,
        @Schema(description = "Role of the user", example = "SUPPLIER")
        String role,
        @Schema(description = "Invitation status of the user", example = "PENDING")
        String status,
        @Schema(description = "Minimarket the user works for", example = "mm-vida-verde")
        String minimarketId,
        @Schema(description = "Supplier company the user works for", example = "sup-bioandes")
        String supplierId) {
}