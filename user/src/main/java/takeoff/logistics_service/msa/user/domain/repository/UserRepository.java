package takeoff.logistics_service.msa.user.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Page<User> findAll(Specification<User> spec, Pageable pageable);
    Page<DeliveryManager> findAllDeliveryManagers(Specification<DeliveryManager> spec, Pageable pageable);
    Optional<DeliveryManager> findDeliveryManagerById(Long id);
    Optional<User> findByUsernameAndPassword(String username, String password);

}
