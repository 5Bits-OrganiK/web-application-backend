package _5bits.organik.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request resource used to create a user")
public record CreateUserResource(
        @Schema(
                description = "Full name of the user",
                example = "Marco Quispe",
                maxLength = 256,
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{user.error.fullName.notBlank}")
        @Size(max = 256, message = "{user.error.fullName.size}")
        String fullName,

        @Schema(
                description = "Email address of the user",
                example = "marco@bioandes.pe",
                maxLength = 256,
                pattern = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{user.error.email.notBlank}")
        @Size(max = 256, message = "{user.error.email.size}")
        @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$", message = "{user.error.email.pattern}")
        String email,

        @Schema(
                description = "Role of the user",
                example = "ADMINISTRATOR",
                allowableValues = {"ADMINISTRATOR", "OPERATOR", "SUPPLIER"},
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{user.error.role.notBlank}")
        @Pattern(regexp = "^(ADMINISTRATOR|OPERATOR|SUPPLIER)$", message = "{user.error.role.pattern}")
        String role,

        @Schema(
                description = "Minimarket the user works for, empty for suppliers",
                example = "mm-vida-verde",
                maxLength = 256,
                pattern = "^[a-zA-Z0-9_-]+$")
        @Size(max = 256, message = "{user.error.minimarketId.size}")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{user.error.minimarketId.pattern}")
        String minimarketId,

        @Schema(
                description = "Supplier company the user works for, empty for minimarket users",
                example = "sup-bioandes",
                maxLength = 256,
                pattern = "^[a-zA-Z0-9_-]+$")
        @Size(max = 256, message = "{user.error.supplierId.size}")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{user.error.supplierId.pattern}")
        String supplierId) { }