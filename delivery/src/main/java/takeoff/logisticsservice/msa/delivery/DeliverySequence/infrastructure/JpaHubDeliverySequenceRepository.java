package takeoff.logisticsservice.msa.delivery.DeliverySequence.infrastructure;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.domain.entity.HubDeliverySequence;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.domain.repository.HubDeliverySequenceRepository;

public interface JpaHubDeliverySequenceRepository extends
    JpaRepository<HubDeliverySequence, UUID>,
    HubDeliverySequenceRepository {

  @Override
  @Query(nativeQuery = true, value = "SELECT current_sequence FROM p_hub_delivery_sequence LIMIT 1")
  Optional<Integer> findCurrentSequence();


  @Override
  @Modifying
  @Query(nativeQuery = true, value = "UPDATE p_hub_delivery_sequence SET current_sequence = :nextSequence")
  void updateCurrentSequence(Integer nextSequence);
}

