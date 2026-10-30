package _5bits.organik.platform.suppliers.interfaces.rest.transform;

import _5bits.organik.platform.suppliers.domain.model.commands.CreateSupplierCommand;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.CreateSupplierResource;

import java.util.stream.Collectors;

public class CreateSupplierCommandFromResourceAssembler {
    public static CreateSupplierCommand toCommandFromResource(CreateSupplierResource resource) {
        var categories = resource.categories().stream()
                .map(SuppliersValueObjectFromStringAssembler::toSupplierCategoryFromString)
                .collect(Collectors.toSet());
        return new CreateSupplierCommand(
                SuppliersValueObjectFromStringAssembler.toBusinessNameFromString(resource.businessName()),
                SuppliersValueObjectFromStringAssembler.toContactNameFromString(resource.contactName()),
                SuppliersValueObjectFromStringAssembler.toPhoneNumberFromString(resource.phoneNumber()),
                SuppliersValueObjectFromStringAssembler.toEmailAddressFromString(resource.email()),
                categories,
                SuppliersValueObjectFromStringAssembler.toOrganicCertificationFromString(resource.organicCertification()));
    }
}
