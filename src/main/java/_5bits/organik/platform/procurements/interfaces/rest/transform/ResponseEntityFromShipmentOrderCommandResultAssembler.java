package _5bits.organik.platform.procurements.interfaces.rest.transform;

import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.interfaces.rest.resources.ShipmentOrderResource;
import org.springframework.http.ResponseEntity;

public final class ResponseEntityFromShipmentOrderCommandResultAssembler {
    private ResponseEntityFromShipmentOrderCommandResultAssembler() {}

    public static ResponseEntity<ShipmentOrderResource> created(ShipmentOrder order) {
        return ResponseEntity.status(201)
                .body(ShipmentOrderResourceFromEntityAssembler.toResourceFromEntity(order));
    }
}
