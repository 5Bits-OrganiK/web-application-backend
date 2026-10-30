package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.application.commandservices.OfferCommandFailure;
import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromOfferCommandResultAssembler {
    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Offer, OfferCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                offer -> new ResponseEntity<>(OfferResourceFromEntityAssembler.toResourceFromEntity(offer), CREATED),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(OfferCommandFailure failure) {
        if (failure instanceof OfferCommandFailure.StockLotNotFound) {
            return HttpStatus.NOT_FOUND;
        }
        if (failure instanceof OfferCommandFailure.EndsAfterExpiration) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}
