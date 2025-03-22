package takeoff.logisticsservice.msa.delivery.DeliverySequence.application;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client.UserClient;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client.response.GetCompanyDeliveryManagerResponseDto;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client.response.GetHubDeliveryManagerResponseDto;
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
    List<GetCompanyDeliveryManagerResponseDto> companyDeliveryManagers = userClient.findAllCompanyDeliveryManagerByHubId(
        hubId);
    companyDeliveryManagers.sort(
        Comparator.comparing(GetCompanyDeliveryManagerResponseDto::deliverySequence));

    Integer maxSequence = companyDeliveryManagers.get(companyDeliveryManagers.size() - 1)
        .deliverySequence();
    Integer minSequence = companyDeliveryManagers.get(0).deliverySequence();

    if (Objects.equals(currentSequence, maxSequence)) {
      Integer nextSequence = companyDeliveryManagers.get(minSequence).deliverySequence();
      Long nextCompanyDeliveryManager = companyDeliveryManagers.get(minSequence).userId();
      companyDeliverySequenceRepository.updateCurrentSequence(nextSequence, hubId);

      return nextCompanyDeliveryManager;
    }

    int currentSequenceIdx = findCurrentSequenceCompanyDeliveryManagerIdx(companyDeliveryManagers, currentSequence);
    Integer nextSequence = companyDeliveryManagers.get(currentSequenceIdx + 1).deliverySequence();
    Long nextCompanyDeliveryManager = companyDeliveryManagers.get(currentSequenceIdx + 1).userId();
    companyDeliverySequenceRepository.updateCurrentSequence(nextSequence, hubId);

    return nextCompanyDeliveryManager;
  }

  private Integer findCurrentSequenceCompanyDeliveryManagerIdx(
      List<GetCompanyDeliveryManagerResponseDto> companyDeliveryManagers, Integer currentSequence) {
    return companyDeliveryManagers.stream()
        .map(GetCompanyDeliveryManagerResponseDto::deliverySequence).toList()
        .indexOf(currentSequence);
  }
  // TODO: 커스텀 예외로 변경

  private Long determineNextHubDeliverySequence(Integer currentSequence) {
    List<GetHubDeliveryManagerResponseDto> hubDeliveryManagers = userClient.findAllHubDeliveryManager();
    hubDeliveryManagers.sort(
        Comparator.comparing(GetHubDeliveryManagerResponseDto::deliverySequence));

    Integer maxSequence = hubDeliveryManagers.get(hubDeliveryManagers.size() - 1).deliverySequence();
    Integer minSequence = hubDeliveryManagers.get(0).deliverySequence();

    if (Objects.equals(currentSequence, maxSequence)) {
      Integer nextSequence = hubDeliveryManagers.get(minSequence).deliverySequence();
      Long nextHubDeliveryManager = hubDeliveryManagers.get(minSequence).userId();
      hubDeliverySequenceRepository.updateCurrentSequence(nextSequence);

      return nextHubDeliveryManager;
    }

    int currentSequenceIdx = findCurrentSequenceHubDeliveryManagerIdx(hubDeliveryManagers, currentSequence);
    Integer nextSequence = hubDeliveryManagers.get(currentSequenceIdx + 1).deliverySequence();
    Long nextCompanyDeliveryManager = hubDeliveryManagers.get(currentSequenceIdx + 1).userId();
    hubDeliverySequenceRepository.updateCurrentSequence(nextSequence);

    return nextCompanyDeliveryManager;

  }

  private Integer findCurrentSequenceHubDeliveryManagerIdx(
      List<GetHubDeliveryManagerResponseDto> hubDeliveryManagers, Integer currentSequence) {
    return hubDeliveryManagers.stream()
        .map(GetHubDeliveryManagerResponseDto::deliverySequence).toList()
        .indexOf(currentSequence);
  }
}
