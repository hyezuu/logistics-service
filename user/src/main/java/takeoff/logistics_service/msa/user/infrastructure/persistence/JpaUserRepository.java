package takeoff.logistics_service.msa.user.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.repository.UserRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository, JpaSpecificationExecutor<User> {
    @Override
    @Query("SELECT u FROM User u WHERE u.slackEmail = :slackEmail AND u.deletedAt IS NULL")
    Optional<User> findByEmail(String email);

    @Override
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.deletedAt IS NULL")
    Optional<User> findByUsername(String username);

    @Override
    @Query("SELECT u FROM User u WHERE u.id = :userId AND u.deletedAt IS NULL")
    Optional<User> findById(Long id);

    @Override
    @Query("SELECT d FROM DeliveryManager d WHERE d.id = :id AND d.deletedAt IS NULL")
    Optional<DeliveryManager> findDeliveryManagerById(Long id);

    @Override
    default Page<DeliveryManager> findAllDeliveryManagers(Specification<DeliveryManager> spec, Pageable pageable) {
        return findAllDeliveryManagers(spec, pageable);
    }

    @Override
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password AND u.deletedAt IS NULL")
    Optional<User> findByUsernameAndPassword(String username, String password);

}
