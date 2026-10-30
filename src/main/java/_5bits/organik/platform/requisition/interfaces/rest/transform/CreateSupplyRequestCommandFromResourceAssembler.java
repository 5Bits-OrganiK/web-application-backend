package _5bits.organik.platform.requisition.interfaces.rest.transform;

import _5bits.organik.platform.requisition.domain.model.commands.CreateSupplyRequestCommand;
import _5bits.organik.platform.requisition.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.requisition.interfaces.rest.resources.CreateSupplyRequestResource;

public class CreateSupplyRequestCommandFromResourceAssembler {
    public static CreateSupplyRequestCommand toCommandFromResource(CreateSupplyRequestResource resource) {
        return new CreateSupplyRequestCommand(
                RequisitionValueObjectFromStringAssembler.toProductIdFromString(resource.productId()),
                RequisitionValueObjectFromStringAssembler.toSupplierIdFromString(resource.supplierId()),
                RequisitionValueObjectFromStringAssembler.toMinimarketIdFromString(resource.minimarketId()),
                new Quantity(resource.quantity()),
                RequisitionValueObjectFromStringAssembler.toRequestReasonFromString(resource.reason()),
                resource.requiredDate(),
                RequisitionValueObjectFromStringAssembler.toRequestPriorityFromString(resource.priority()));
    }
}
