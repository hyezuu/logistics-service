package takeoff.logisticsservice.msa.delivery.DeliverySequence.infrastructure;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.domain.entity.CompanyDeliverySequence;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.domain.repository.CompanyDeliverySequenceRepository;

public interface JpaCompanyDeliverySequenceRepository extends
    JpaRepository<CompanyDeliverySequence, UUID>,
    CompanyDeliverySequenceRepository {

  @Override
  @Query(nativeQuery = true, value = "SELECT current_sequence FROM p_company_delivery_sequence WHERE hub_Id = :hubId LIMIT 1")
  Optional<Integer> findCurrentSequence(UUID hubId);

  @Override
  @Modifying
  @Query(nativeQuery = true, value = "UPDATE p_company_delivery_sequence SET current_sequence = :nextSequence WHERE hub_id = :hubId")
  void updateCurrentSequence(Integer nextSequence, UUID hubId);
}

