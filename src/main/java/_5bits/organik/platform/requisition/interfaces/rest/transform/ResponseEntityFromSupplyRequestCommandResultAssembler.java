package _5bits.organik.platform.requisition.interfaces.rest.transform;

import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromSupplyRequestCommandResultAssembler {
    public static ResponseEntity<?> toResponseEntityFromSupplyRequest(SupplyRequest supplyRequest) {
        return new ResponseEntity<>(SupplyRequestResourceFromEntityAssembler.toResourceFromEntity(supplyRequest), CREATED);
    }
}
