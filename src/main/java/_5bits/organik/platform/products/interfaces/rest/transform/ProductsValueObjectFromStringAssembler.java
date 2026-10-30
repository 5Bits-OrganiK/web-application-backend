package _5bits.organik.platform.products.interfaces.rest.transform;

import _5bits.organik.platform.products.domain.model.valueobjects.CategoryId;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryName;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryTone;
import _5bits.organik.platform.products.domain.model.valueobjects.MeasurementUnit;
import _5bits.organik.platform.products.domain.model.valueobjects.MinimumStock;
import _5bits.organik.platform.products.domain.model.valueobjects.ProductName;
import _5bits.organik.platform.products.domain.model.valueobjects.Sku;
import _5bits.organik.platform.products.domain.model.valueobjects.SupplierId;

public class ProductsValueObjectFromStringAssembler {
    public static CategoryName toCategoryNameFromString(String value) {
        return new CategoryName(value);
    }

    public static CategoryTone toCategoryToneFromString(String value) {
        return CategoryTone.valueOf(value);
    }

    public static Sku toSkuFromString(String value) {
        return new Sku(value);
    }

    public static ProductName toProductNameFromString(String value) {
        return new ProductName(value);
    }

    public static CategoryId toCategoryIdFromLong(Long value) {
        return new CategoryId(value);
    }

    public static SupplierId toSupplierIdFromString(String value) {
        return value == null || value.isBlank() ? null : new SupplierId(value);
    }

    public static MeasurementUnit toMeasurementUnitFromString(String value) {
        return MeasurementUnit.valueOf(value);
    }

    public static MinimumStock toMinimumStockFromInteger(Integer value) {
        return new MinimumStock(value);
    }
}
