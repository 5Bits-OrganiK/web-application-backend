package _5bits.organik.platform.procurements.interfaces.rest.transform;

import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.interfaces.rest.resources.ShipmentOrderResource;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public final class ResponseEntityFromShipmentOrderQueryResultAssembler {
    private ResponseEntityFromShipmentOrderQueryResultAssembler() {}

    public static ResponseEntity<ShipmentOrderResource> ok(ShipmentOrder order) {
        return ResponseEntity.ok(ShipmentOrderResourceFromEntityAssembler.toResourceFromEntity(order));
    }

    public static ResponseEntity<List<ShipmentOrderResource>> ok(List<ShipmentOrder> orders) {
        return ResponseEntity.ok(orders.stream()
                .map(ShipmentOrderResourceFromEntityAssembler::toResourceFromEntity)
                .toList());
    }

    public static ResponseEntity<ProblemDetail> badRequest(MessageSource messages, String key) {
        return problem(HttpStatus.BAD_REQUEST, messages, key);
    }

    public static ResponseEntity<ProblemDetail> notFound(MessageSource messages, String key, Object... args) {
        return problem(HttpStatus.NOT_FOUND, messages, key, args);
    }

    private static ResponseEntity<ProblemDetail> problem(
            HttpStatus status, MessageSource messages, String key, Object... args) {
        var detail = messages.getMessage(key, args, key, LocaleContextHolder.getLocale());
        return ResponseEntity.status(status)
                .body(ProblemDetail.forStatusAndDetail(status, detail));
    }
}
