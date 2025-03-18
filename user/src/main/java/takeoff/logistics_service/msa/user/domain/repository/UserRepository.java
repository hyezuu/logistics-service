package takeoff.logistics_service.msa.user.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.HubId;
import takeoff.logistics_service.msa.user.domain.vo.SlackId;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Page<User> findAll(Specification<User> spec, Pageable pageable);
    Optional<DeliveryManager> findDeliveryManagerById(Long id);
    Page<DeliveryManager> findAllDeliveryManagers(Pageable pageable);
}
