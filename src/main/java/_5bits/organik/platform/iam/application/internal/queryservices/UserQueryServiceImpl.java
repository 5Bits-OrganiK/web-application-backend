package _5bits.organik.platform.iam.application.internal.queryservices;

import _5bits.organik.platform.iam.application.queryservices.UserQueryService;
import _5bits.organik.platform.iam.domain.model.aggregates.User;
import _5bits.organik.platform.iam.domain.model.queries.GetAllUsersByMinimarketIdQuery;
import _5bits.organik.platform.iam.domain.model.queries.GetUserByEmailQuery;
import _5bits.organik.platform.iam.domain.model.queries.GetUserByIdQuery;
import _5bits.organik.platform.iam.infrastructure.persistence.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional (readOnly = true)
public class UserQueryServiceImpl implements UserQueryService{
    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> handle(GetAllUsersByMinimarketIdQuery query) {
        log.debug("Querying all users for minimarketId={}", query.minimarketId().value());
        var results =  userRepository.findAllByMinimarketId(query.minimarketId());
        log.debug("Found {} user(s) for minimarketId={}", results.size(),  query.minimarketId().value());
        return results;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        log.debug("Querying user by id={}", query.id());
        var result = userRepository.findById(query.id());
        if (result.isEmpty()) log.debug("No user found for id={}", query.id());
        return result;
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        log.debug("Querying user by email={}", query.email().value());
        var result = userRepository.findByEmail(query.email());
        if (result.isEmpty()) log.debug("No user found for email={}", query.email().value());
        return result;
    }
}
