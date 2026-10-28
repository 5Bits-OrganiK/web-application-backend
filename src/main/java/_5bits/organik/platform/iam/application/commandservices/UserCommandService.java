package _5bits.organik.platform.iam.application.commandservices;

import _5bits.organik.platform.iam.domain.model.aggregates.User;
import _5bits.organik.platform.iam.domain.model.commands.CreateUserCommand;
import _5bits.organik.platform.shared.application.result.Result;

public interface UserCommandService {
    Result<User, UserCommandFailure> handle(CreateUserCommand command);
}
