package takeoff.logistics_service.msa.user.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import takeoff.logistics_service.msa.user.domain.entity.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findBySlackEmail(String slackEmail);
    Optional<User> findByUsername(String username);
    Page<User> findAll(Specification<User> spec, Pageable pageable);
    Page<DeliveryManager> findAllDeliveryManagers(Specification<DeliveryManager> spec, Pageable pageable);
    Optional<DeliveryManager> findDeliveryManagerById(Long id);
    List<CompanyDeliveryManager> findAllCompanyDeliveryManagersByHubId(UUID hubId);
    List<HubDeliveryManager> findAllHubDeliveryManagers();
    List<User> findAllByCompanyId(UUID companyId);
    List<User> findAllByHubId(UUID hubId);
    Optional<CompanyManager> findCompanyManagerById(Long id);
    Optional<HubManager> findHubManagerById(Long id);
    int countCompanyDeliveryManagersByHubId(UUID hubId);
    int countHubDeliveryManagersByHubId(UUID hubId);
}
