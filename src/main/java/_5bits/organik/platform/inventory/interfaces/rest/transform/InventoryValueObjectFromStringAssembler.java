package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementReason;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementType;
import _5bits.organik.platform.inventory.domain.model.valueobjects.ProductId;

public class InventoryValueObjectFromStringAssembler {
    public static MinimarketId toMinimarketIdFromString(String value) {
        return new MinimarketId(value);
    }

    public static ProductId toProductIdFromString(String value) {
        return new ProductId(value);
    }

    public static LotCode toLotCodeFromString(String value) {
        return new LotCode(value);
    }

    public static MovementType toMovementTypeFromString(String value) {
        return MovementType.valueOf(value);
    }

    public static MovementReason toMovementReasonFromString(String value) {
        return value == null || value.isBlank() ? null : new MovementReason(value);
    }
}
