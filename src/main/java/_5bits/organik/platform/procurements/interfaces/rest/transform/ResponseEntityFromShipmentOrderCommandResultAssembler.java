package _5bits.organik.platform.procurements.interfaces.rest.transform;

import _5bits.organik.platform.procurements.application.commandservices.ShipmentOrderCommandFailure;
import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.interfaces.rest.resources.ShipmentOrderResource;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public final class ResponseEntityFromShipmentOrderCommandResultAssembler {
    private ResponseEntityFromShipmentOrderCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<ShipmentOrder, ShipmentOrderCommandFailure> result,
            MessageSource messageSource,
            HttpStatus successStatus) {
        return result.fold(
                order -> ResponseEntity.status(successStatus)
                        .body(ShipmentOrderResourceFromEntityAssembler.toResourceFromEntity(order)),
                failure -> {
                    var status = statusFromFailure(failure);
                    var detail = messageSource.getMessage(
                            failure.messageKey(), null, failure.messageKey(), LocaleContextHolder.getLocale());
                    return ResponseEntity.status(status)
                            .body(ProblemDetail.forStatusAndDetail(status, detail));
                });
    }

    private static HttpStatus statusFromFailure(ShipmentOrderCommandFailure failure) {
        if (failure instanceof ShipmentOrderCommandFailure.NotFound) {
            return HttpStatus.NOT_FOUND;
        }
        if (failure instanceof ShipmentOrderCommandFailure.InvalidState) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
