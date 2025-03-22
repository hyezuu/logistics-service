package takeoff.logisticsservice.msa.delivery.DeliverySequence.domain.repository;

import java.util.Optional;

public interface HubDeliverySequenceRepository {

  Optional<Integer> findCurrentSequence();

  void updateCurrentSequence(Integer nextSequence);
}
