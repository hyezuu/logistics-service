package takeoff.logisticsservice.msa.delivery.DeliverySequence.domain.repository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyDeliverySequenceRepository {

  Optional<Integer> findCurrentSequence(UUID hubId);

  void updateCurrentSequence(Integer nextSequence, UUID hubId);
}
