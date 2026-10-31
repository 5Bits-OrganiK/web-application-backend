package _5bits.organik.platform.products.interfaces.rest.transform;

import _5bits.organik.platform.products.domain.model.commands.CreateProductCommand;
import _5bits.organik.platform.products.interfaces.rest.resources.CreateProductResource;

public class CreateProductCommandFromResourceAssembler {
    public static CreateProductCommand toCommandFromResource(CreateProductResource resource) {
        return new CreateProductCommand(
                ProductsValueObjectFromStringAssembler.toSkuFromString(resource.sku()),
                ProductsValueObjectFromStringAssembler.toProductNameFromString(resource.name()),
                ProductsValueObjectFromStringAssembler.toCategoryIdFromLong(resource.categoryId()),
                ProductsValueObjectFromStringAssembler.toSupplierIdFromString(resource.supplierId()),
                ProductsValueObjectFromStringAssembler.toMeasurementUnitFromString(resource.unit()),
                ProductsValueObjectFromStringAssembler.toMinimumStockFromInteger(resource.minimumStock()));
    }
}
