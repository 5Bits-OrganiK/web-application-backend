package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.application.commandservices.InventoryMovementCommandFailure;
import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromInventoryMovementCommandResultAssembler {
    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<InventoryMovement, InventoryMovementCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                movement -> new ResponseEntity<>(InventoryMovementResourceFromEntityAssembler.toResourceFromEntity(movement), CREATED),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(InventoryMovementCommandFailure failure) {
        if (failure instanceof InventoryMovementCommandFailure.StockLotNotFound) {
            return HttpStatus.NOT_FOUND;
        }
        if (failure instanceof InventoryMovementCommandFailure.InsufficientStock) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}
