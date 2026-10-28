package _5bits.organik.platform.iam.application.internal.commandservices;

import _5bits.organik.platform.iam.application.commandservices.UserCommandService;
import _5bits.organik.platform.iam.application.commandservices.UserCommandFailure;
import _5bits.organik.platform.iam.domain.model.aggregates.User;
import _5bits.organik.platform.iam.domain.model.commands.CreateUserCommand;
import _5bits.organik.platform.iam.infrastructure.persistence.jpa.UserRepository;
import _5bits.organik.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static _5bits.organik.platform.iam.domain.model.aggregates.User.EMAIL_UNIQUE_CONSTRAINT;

@Slf4j
@Service
public class UserCommandServiceImpl implements UserCommandService {
    private static final String DUPLICATE_USER_CONSTRAINT = EMAIL_UNIQUE_CONSTRAINT;
    private final UserRepository userRepository;

    public UserCommandServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Result<User, UserCommandFailure> handle(CreateUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            log.warn("Duplicate user detected (pre-check): email={}", command.email().value());
            return duplicateResult();
        }
        try {
            var user = new User(command);
            var createdUser = userRepository.save(user);
            log.info("User created: email={}, role={}, id={}, createdAt={}, updatedAt={}",
                    command.email().value(),
                    command.role(),
                    createdUser.getId(),
                    createdUser.getCreatedAt(),
                    createdUser.getUpdatedAt());
            return Result.success(createdUser);
        } catch (DataIntegrityViolationException exception) {
            if (isDuplicateUserViolation(exception)) {
                log.warn("Duplicate user detected (constraint violation):  email={}", command.email().value());
                return duplicateResult();
            }
            log.error("Unexpected data integrity violation saving user: email={}", command.email().value(), exception);
            throw exception;
        }
    }

    private Result<User, UserCommandFailure> duplicateResult() {
        return Result.failure(new UserCommandFailure.Duplicate());
    }

    private boolean isDuplicateUserViolation(DataIntegrityViolationException exception) {
        Throwable violationCause = exception;
        while (violationCause != null) {
            String message = violationCause.getMessage();
            if (message != null && message.contains(DUPLICATE_USER_CONSTRAINT)) return true;
            violationCause = violationCause.getCause();
        }
        return false;
    }
}
