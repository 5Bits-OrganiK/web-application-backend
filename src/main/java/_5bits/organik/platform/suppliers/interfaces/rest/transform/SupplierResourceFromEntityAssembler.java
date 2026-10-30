package _5bits.organik.platform.suppliers.interfaces.rest.transform;

import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.SupplierResource;

public class SupplierResourceFromEntityAssembler {
    public static SupplierResource toResourceFromEntity(Supplier entity) {
        var categories = entity.getCategories().stream()
                .map(Enum::name)
                .sorted()
                .toList();
        return new SupplierResource(
                entity.getId(),
                entity.getBusinessName().value(),
                entity.getContactName().value(),
                entity.getPhoneNumber().value(),
                entity.getEmail().value(),
                categories,
                entity.getOrganicCertification().value());
    }
}
