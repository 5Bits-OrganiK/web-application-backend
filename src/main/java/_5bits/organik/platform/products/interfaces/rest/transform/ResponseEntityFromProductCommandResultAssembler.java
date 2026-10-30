package _5bits.organik.platform.products.interfaces.rest.transform;

import _5bits.organik.platform.products.application.commandservices.ProductCommandFailure;
import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromProductCommandResultAssembler {
    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Product, ProductCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                product -> new ResponseEntity<>(ProductResourceFromEntityAssembler.toResourceFromEntity(product), CREATED),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(ProductCommandFailure failure) {
        if (failure instanceof ProductCommandFailure.Duplicate) {
            return HttpStatus.CONFLICT;
        }
        if (failure instanceof ProductCommandFailure.CategoryNotFound) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}
