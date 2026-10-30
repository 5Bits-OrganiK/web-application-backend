package _5bits.organik.platform.products.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request resource used to create a product category")
public record CreateCategoryResource(
        @Schema(
                description = "Name of the category",
                example = "Fruits and vegetables",
                maxLength = 128,
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{category.error.name.notBlank}")
        @Size(max = 128, message = "{category.error.name.size}")
        String name,

        @Schema(
                description = "Accent color used to show the category in the catalog",
                example = "GREEN",
                allowableValues = {"GREEN", "BLUE", "ORANGE", "YELLOW", "NEUTRAL"},
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{category.error.tone.notBlank}")
        @Pattern(regexp = "^(GREEN|BLUE|ORANGE|YELLOW|NEUTRAL)$", message = "{category.error.tone.pattern}")
        String tone) { }
