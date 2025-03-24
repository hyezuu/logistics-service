package takeoff.logisticsservice.msa.delivery.DeliverySequence.application;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client.UserClient;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client.response.GetDeliveryManagerListInfoDto;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.dto.request.GetCompanyDeliverySequenceRequestDto;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.dto.response.GetCompanyDeliverySequenceResponseDto;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.dto.response.GetHubDeliverySequenceResponseDto;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.domain.repository.CompanyDeliverySequenceRepository;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.domain.repository.HubDeliverySequenceRepository;

@Service
@RequiredArgsConstructor
public class DeliverySequenceService {

  private final UserClient userClient;
  private final CompanyDeliverySequenceRepository companyDeliverySequenceRepository;
  private final HubDeliverySequenceRepository hubDeliverySequenceRepository;

  @Transactional
  public GetCompanyDeliverySequenceResponseDto findNextCompanyDeliverySequence(
      GetCompanyDeliverySequenceRequestDto dto) {
    Integer currentSequence = companyDeliverySequenceRepository.findCurrentSequence(dto.hubId())
        .orElse(-1);

    Long nextCompanyDeliveryManager = determineNextCompanyDeliveryManager(currentSequence,
        dto.hubId());

    return new GetCompanyDeliverySequenceResponseDto(nextCompanyDeliveryManager);
  }

  @Transactional
  public GetHubDeliverySequenceResponseDto findNextHubDeliverySequence() {
    Integer currentSequence = hubDeliverySequenceRepository.findCurrentSequence().orElse(-1);

    Long nextHubDeliveryManager = determineNextHubDeliverySequence(currentSequence);

    return new GetHubDeliverySequenceResponseDto(nextHubDeliveryManager);
  }

  private Long determineNextCompanyDeliveryManager(Integer currentSequence, UUID hubId) {
    List<GetDeliveryManagerListInfoDto> companyDeliveryManagers = userClient.findAllCompanyDeliveryManagerByHubId(
        hubId).deliveryManagers();
    companyDeliveryManagers.sort(
        Comparator.comparing(GetDeliveryManagerListInfoDto::sequenceNumber));

    Integer maxSequence = companyDeliveryManagers.get(companyDeliveryManagers.size() - 1)
        .sequenceNumber();
    Integer minSequence = companyDeliveryManagers.get(0).sequenceNumber();

    if (Objects.equals(currentSequence, maxSequence)) {
      Integer nextSequence = companyDeliveryManagers.get(minSequence).sequenceNumber();
      Long nextCompanyDeliveryManager = companyDeliveryManagers.get(minSequence)
          .deliveryManagerId();
      companyDeliverySequenceRepository.updateCurrentSequence(nextSequence, hubId);

      return nextCompanyDeliveryManager;
    }

    int currentSequenceIdx = findCurrentSequenceCompanyDeliveryManagerIdx(companyDeliveryManagers,
        currentSequence);
    Integer nextSequence = companyDeliveryManagers.get(currentSequenceIdx + 1).sequenceNumber();
    Long nextCompanyDeliveryManager = companyDeliveryManagers.get(currentSequenceIdx + 1)
        .deliveryManagerId();
    companyDeliverySequenceRepository.updateCurrentSequence(nextSequence, hubId);

    return nextCompanyDeliveryManager;
  }

  private Integer findCurrentSequenceCompanyDeliveryManagerIdx(
      List<GetDeliveryManagerListInfoDto> companyDeliveryManagers, Integer currentSequence) {
    return companyDeliveryManagers.stream()
        .map(GetDeliveryManagerListInfoDto::sequenceNumber)
        .toList()
        .indexOf(currentSequence);
  }

  private Long determineNextHubDeliverySequence(Integer currentSequence) {
    List<GetDeliveryManagerListInfoDto> hubDeliveryManagers = userClient.findAllHubDeliveryManager()
        .deliveryManagers();
    hubDeliveryManagers.sort(
        Comparator.comparing(GetDeliveryManagerListInfoDto::sequenceNumber));

    Integer maxSequence = hubDeliveryManagers.get(hubDeliveryManagers.size() - 1)
        .sequenceNumber();
    Integer minSequence = hubDeliveryManagers.get(0).sequenceNumber();

    if (Objects.equals(currentSequence, maxSequence)) {
      Integer nextSequence = hubDeliveryManagers.get(minSequence).sequenceNumber();
      Long nextHubDeliveryManager = hubDeliveryManagers.get(minSequence).deliveryManagerId();
      hubDeliverySequenceRepository.updateCurrentSequence(nextSequence);

      return nextHubDeliveryManager;
    }

    int currentSequenceIdx = findCurrentSequenceHubDeliveryManagerIdx(hubDeliveryManagers,
        currentSequence);
    Integer nextSequence = hubDeliveryManagers.get(currentSequenceIdx + 1).sequenceNumber();
    Long nextCompanyDeliveryManager = hubDeliveryManagers.get(currentSequenceIdx + 1)
        .deliveryManagerId();
    hubDeliverySequenceRepository.updateCurrentSequence(nextSequence);

    return nextCompanyDeliveryManager;

  }

  private Integer findCurrentSequenceHubDeliveryManagerIdx(
      List<GetDeliveryManagerListInfoDto> hubDeliveryManagers, Integer currentSequence) {
    return hubDeliveryManagers.stream()
        .map(GetDeliveryManagerListInfoDto::sequenceNumber)
        .toList()
        .indexOf(currentSequence);
  }
}
