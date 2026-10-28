package _5bits.organik.platform.iam.application.queryservices;

import _5bits.organik.platform.iam.domain.model.aggregates.User;
import _5bits.organik.platform.iam.domain.model.queries.GetAllUsersByMinimarketIdQuery;
import _5bits.organik.platform.iam.domain.model.queries.GetUserByIdQuery;
import _5bits.organik.platform.iam.domain.model.queries.GetUserByEmailQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersByMinimarketIdQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByEmailQuery query);
}
