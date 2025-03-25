package takeoff.logistics_service.msa.user.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import takeoff.logistics_service.msa.user.domain.entity.CompanyDeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.CompanyManager;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.Employee;
import takeoff.logistics_service.msa.user.domain.entity.HubDeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.HubManager;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findBySlackEmail(String slackEmail);
    Optional<User> findByUsername(String username);
    Page<User> findAll(Specification<User> spec, Pageable pageable);
    Page<DeliveryManager> findAllDeliveryManagers(Specification<DeliveryManager> spec, Pageable pageable);
    Optional<DeliveryManager> findDeliveryManagerById(Long id);
    List<CompanyDeliveryManager> findAllCompanyDeliveryManagersByHubId(HubId hubId);
    List<HubDeliveryManager> findAllHubDeliveryManagers();
    List<Employee> findAllEmployeesByCompanyId(CompanyId companyId);
    List<Employee> findAllEmployeesByHubId(HubId hubId);
    Optional<CompanyManager> findCompanyManagerById(Long id);
    Optional<HubManager> findHubManagerById(Long id);
    int countCompanyDeliveryManagersByHubId(HubId hubId);
    int countHubDeliveryManagersByHubId(HubId hubId);
}
