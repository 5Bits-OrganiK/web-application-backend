package _5bits.organik.platform.products.interfaces.rest.transform;

import _5bits.organik.platform.products.domain.model.commands.CreateCategoryCommand;
import _5bits.organik.platform.products.interfaces.rest.resources.CreateCategoryResource;

public class CreateCategoryCommandFromResourceAssembler {
    public static CreateCategoryCommand toCommandFromResource(CreateCategoryResource resource) {
        return new CreateCategoryCommand(
                ProductsValueObjectFromStringAssembler.toCategoryNameFromString(resource.name()),
                ProductsValueObjectFromStringAssembler.toCategoryToneFromString(resource.tone()));
    }
}
