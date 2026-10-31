package _5bits.organik.platform.products.interfaces.rest.transform;

import _5bits.organik.platform.products.application.commandservices.CategoryCommandFailure;
import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromCategoryCommandResultAssembler {
    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Category, CategoryCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                category -> new ResponseEntity<>(CategoryResourceFromEntityAssembler.toResourceFromEntity(category), CREATED),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(CategoryCommandFailure failure) {
        if (failure instanceof CategoryCommandFailure.Duplicate) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}
