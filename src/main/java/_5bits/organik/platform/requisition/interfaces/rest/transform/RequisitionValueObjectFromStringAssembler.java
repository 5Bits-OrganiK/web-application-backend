package _5bits.organik.platform.requisition.interfaces.rest.transform;

import _5bits.organik.platform.requisition.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.RequestPriority;
import _5bits.organik.platform.requisition.domain.model.valueobjects.RequestReason;
import _5bits.organik.platform.requisition.domain.model.valueobjects.SupplierId;

public class RequisitionValueObjectFromStringAssembler {
    public static ProductId toProductIdFromString(String value) {
        return new ProductId(value);
    }

    public static SupplierId toSupplierIdFromString(String value) {
        return new SupplierId(value);
    }

    public static MinimarketId toMinimarketIdFromString(String value) {
        return new MinimarketId(value);
    }

    public static RequestReason toRequestReasonFromString(String value) {
        return new RequestReason(value);
    }

    public static RequestPriority toRequestPriorityFromString(String value) {
        return RequestPriority.valueOf(value);
    }
}
