package _5bits.organik.platform.suppliers.interfaces.rest.transform;

import _5bits.organik.platform.suppliers.domain.model.commands.CreateOfferedProductCommand;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.CreateOfferedProductResource;

public class CreateOfferedProductCommandFromResourceAssembler {
    public static CreateOfferedProductCommand toCommandFromResource(CreateOfferedProductResource resource) {
        return new CreateOfferedProductCommand(
                SuppliersValueObjectFromStringAssembler.toSupplierIdFromString(resource.supplierId()),
                SuppliersValueObjectFromStringAssembler.toProductIdFromString(resource.productId()),
                SuppliersValueObjectFromStringAssembler.toLotCodeFromString(resource.lotCode()),
                new Quantity(resource.availableQuantity()),
                resource.expiresOn());
    }
}
