package _5bits.organik.platform.iam.interfaces.rest.transform;

import _5bits.organik.platform.iam.application.commandservices.UserCommandFailure;
import _5bits.organik.platform.iam.domain.model.aggregates.User;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromUserCommandResultAssembler {
    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<User, UserCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                user -> new ResponseEntity<>(UserResourceFromEntityAssembler.toResourceFromEntity(user), CREATED),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(UserCommandFailure failure) {
        if (failure instanceof UserCommandFailure.Duplicate) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}