package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseEntityFromInventoryMovementQueryResultAssembler {
    public static ResponseEntity<?> toResponseEntityFromInventoryMovement(InventoryMovement movement) {
        return ResponseEntity.ok(InventoryMovementResourceFromEntityAssembler.toResourceFromEntity(movement));
    }

    public static ResponseEntity<?> toResponseEntityFromList(List<InventoryMovement> movements) {
        var movementResources = movements.stream()
                .map(InventoryMovementResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(movementResources);
    }

    public static ResponseEntity<ProblemDetail> badRequest(MessageSource messageSource, String messageKey, Object... args) {
        return problemDetail(HttpStatus.BAD_REQUEST, messageSource, messageKey, args);
    }

    public static ResponseEntity<ProblemDetail> notFound(MessageSource messageSource, String messageKey, Object... args) {
        return problemDetail(HttpStatus.NOT_FOUND, messageSource, messageKey, args);
    }

    private static ResponseEntity<ProblemDetail> problemDetail(HttpStatus status, MessageSource messageSource, String messageKey, Object... args) {
        var detail = messageSource.getMessage(messageKey, args, messageKey, LocaleContextHolder.getLocale());
        return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(status, detail));
    }
}
