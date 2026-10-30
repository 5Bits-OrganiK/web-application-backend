package _5bits.organik.platform.inventory.interfaces.rest.transform;

import _5bits.organik.platform.inventory.application.commandservices.StockLotCommandFailure;
import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromStockLotCommandResultAssembler {
    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<StockLot, StockLotCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                stockLot -> new ResponseEntity<>(StockLotResourceFromEntityAssembler.toResourceFromEntity(stockLot), CREATED),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(StockLotCommandFailure failure) {
        if (failure instanceof StockLotCommandFailure.Duplicate) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}
