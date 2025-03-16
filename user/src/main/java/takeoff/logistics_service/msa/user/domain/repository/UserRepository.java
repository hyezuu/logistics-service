package takeoff.logistics_service.msa.user.domain.repository;

import takeoff.logistics_service.msa.user.domain.entity.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID userId);
    Optional<User> findByEmail(String email);
    User save(User user);
    void delete(User user);
}
