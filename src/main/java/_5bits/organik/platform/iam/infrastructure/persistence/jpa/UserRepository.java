package _5bits.organik.platform.iam.infrastructure.persistence.jpa;

import _5bits.organik.platform.iam.domain.model.aggregates.User;
import _5bits.organik.platform.iam.domain.model.valueobjects.EmailAddress;
import _5bits.organik.platform.iam.domain.model.valueobjects.MinimarketId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByMinimarketId(MinimarketId minimarketId);
    boolean existsByEmail(EmailAddress email);
    Optional<User> findByEmail(EmailAddress email);
}
