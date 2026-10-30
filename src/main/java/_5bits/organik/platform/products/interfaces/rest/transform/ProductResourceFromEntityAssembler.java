package _5bits.organik.platform.products.interfaces.rest.transform;

import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.interfaces.rest.resources.ProductResource;

public class ProductResourceFromEntityAssembler {
    public static ProductResource toResourceFromEntity(Product entity) {
        return new ProductResource(
                entity.getId(),
                entity.getSku().value(),
                entity.getName().value(),
                entity.getCategoryId().value(),
                entity.getSupplierId() == null ? null : entity.getSupplierId().value(),
                entity.getUnit().name(),
                entity.getMinimumStock().value());
    }
}
