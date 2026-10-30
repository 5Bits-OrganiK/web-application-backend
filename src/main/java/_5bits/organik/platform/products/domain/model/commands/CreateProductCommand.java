package _5bits.organik.platform.products.domain.model.commands;

import _5bits.organik.platform.products.domain.model.valueobjects.CategoryId;
import _5bits.organik.platform.products.domain.model.valueobjects.MeasurementUnit;
import _5bits.organik.platform.products.domain.model.valueobjects.MinimumStock;
import _5bits.organik.platform.products.domain.model.valueobjects.ProductName;
import _5bits.organik.platform.products.domain.model.valueobjects.Sku;
import _5bits.organik.platform.products.domain.model.valueobjects.SupplierId;

public record CreateProductCommand(Sku sku, ProductName name, CategoryId categoryId, SupplierId supplierId,
                                   MeasurementUnit unit, MinimumStock minimumStock) {
    public CreateProductCommand {
        if (sku == null) {
            throw new IllegalArgumentException("Sku cannot be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("ProductName cannot be null");
        }
        if (categoryId == null) {
            throw new IllegalArgumentException("CategoryId cannot be null");
        }
        if (unit == null) {
            throw new IllegalArgumentException("MeasurementUnit cannot be null");
        }
        if (minimumStock == null) {
            throw new IllegalArgumentException("MinimumStock cannot be null");
        }
    }
}
