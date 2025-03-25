package takeoff.logistics_service.msa.user.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import takeoff.logistics_service.msa.user.domain.entity.CompanyDeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.CompanyManager;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.Employee;
import takeoff.logistics_service.msa.user.domain.entity.HubDeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.HubManager;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.repository.UserRepository;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.HubId;
import takeoff.logistics_service.msa.user.infrastructure.persistence.custom.CustomUserRepository;

public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository, JpaSpecificationExecutor<User>, CustomUserRepository {
    @Override
    @Query("SELECT u FROM User u WHERE u.slackEmail = :slackEmail AND u.deletedAt IS NULL")
    Optional<User> findBySlackEmail(String slackEmail);

    @Override
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.deletedAt IS NULL")
    Optional<User> findByUsername(String username);

    @Override
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.deletedAt IS NULL")
    Optional<User> findById(Long id);

    @Override
    @Query("SELECT d FROM DeliveryManager d WHERE d.id = :id AND d.deletedAt IS NULL")
    Optional<DeliveryManager> findDeliveryManagerById(Long id);

    @Override
    @Query("SELECT m FROM CompanyDeliveryManager m WHERE m.hubId = :hubId AND m.deletedAt IS NULL")
    List<CompanyDeliveryManager> findAllCompanyDeliveryManagersByHubId(HubId hubId);

    @Override
    @Query("SELECT m FROM HubDeliveryManager m WHERE m.deliveryManagerType = 'HUB_DELIVERY_MANAGER' AND m.deletedAt IS NULL")
    List<HubDeliveryManager> findAllHubDeliveryManagers();


    @Override
    @Query("SELECT e FROM Employee e WHERE e.companyId = :companyId AND e.deletedAt IS NULL")
    List<Employee> findAllEmployeesByCompanyId(@Param("companyId") CompanyId companyId);

    @Override
    @Query("SELECT e FROM Employee e WHERE e.hubId = :hubId AND e.deletedAt IS NULL")
    List<Employee> findAllEmployeesByHubId(@Param("hubId") HubId hubId);

    @Override
    @Query("SELECT m FROM CompanyManager m WHERE m.id = :id AND m.deletedAt IS NULL")
    Optional<CompanyManager> findCompanyManagerById(Long id);

    @Override
    @Query("SELECT m FROM HubManager m WHERE m.id = :id AND m.deletedAt IS NULL")
    Optional<HubManager> findHubManagerById(Long id);

    @Override
    @Query("SELECT COUNT(m) FROM CompanyDeliveryManager m WHERE m.hubId = :hubId AND m.deletedAt IS NULL")
    int countCompanyDeliveryManagersByHubId(HubId hubId);

    @Override
    @Query("SELECT COUNT(m) FROM HubDeliveryManager m WHERE m.hubId = :hubId AND m.deletedAt IS NULL")
    int countHubDeliveryManagersByHubId(HubId hubId);


}
