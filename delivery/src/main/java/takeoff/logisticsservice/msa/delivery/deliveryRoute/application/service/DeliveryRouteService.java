package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.DeliverySequenceClientInternalDeliveryRoute;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.HubClient;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.request.PostHubRouteRequestDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.response.GetHubDeliverySequenceResponseDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.response.PostHubRouteResponseDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.dto.request.PostDeliveryRoutesRequestDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.dto.response.GetDeliveryRouteResponseDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRoute;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.repository.DeliveryRouteRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryRouteService {

  private final DeliveryRouteRepository deliveryRouteRepository;
  private final HubClient hubClient;
  private final DeliverySequenceClientInternalDeliveryRoute deliverySequenceClient;

  @Transactional
  public List<UUID> saveDeliveryRoutes(PostDeliveryRoutesRequestDto dto) {
    PostHubRouteResponseDto postHubRouteResponseDto = hubClient.postHubRoute(
        new PostHubRouteRequestDto(dto.departureHubId(), dto.destinationHubId()));

//    List<FindHubRoutes> routes = postHubRouteResponseDto.hubAllListResponseList();

    log.info(String.valueOf(postHubRouteResponseDto.hubAllListResponseList().indexOf(0)));

    GetHubDeliverySequenceResponseDto nextHubDeliverySequence = deliverySequenceClient.findNextHubDeliverySequence();

//    GetHubDeliverySequenceResponseDto nextHubDeliverySequence = deliverySequenceClient.findNextHubDeliverySequence();
//    Long deliveryManagerId =

    // 한 배송에 대한 허브 배송 담당자는 한명이다.

    List<DeliveryRoute> deliveryRoutes = postHubRouteResponseDto.hubAllListResponseList().stream()
        .map(route -> DeliveryRoute.builder()
            .id(UUID.randomUUID())
            .deliveryId(dto.deliveryId())
            .deliveryManagerId(nextHubDeliverySequence.nextHubDeliveryManagerId())
            .sequenceNumber(1 + postHubRouteResponseDto.hubAllListResponseList().stream().toList().indexOf(route))
            .fromHubId(route.fromHubId())
            .toHubId(route.toHubId())
            .estimatedDistance(route.getDistance())
            .estimatedDuration(route.getDuration())
            .actualDuration(0)
            .actualDistance(0)
            .build())
        .toList();

    deliveryRouteRepository.saveAll(deliveryRoutes);

    return deliveryRoutes.stream()
        .map(DeliveryRoute::getId)
        .toList();
  }

  @Transactional(readOnly = true)
  public GetDeliveryRouteResponseDto findAllDeliveryRoutesByDeliveryId(UUID deliveryId) {
    List<DeliveryRoute> deliveryRoutes = deliveryRouteRepository.findAllByDeliveryId(deliveryId);
    return GetDeliveryRouteResponseDto.of(deliveryRoutes);
  }

  @Transactional(readOnly = true)
  public List<UUID> findAllDeliveryRoutes_DeliveryIdByDeliveryManagerId(Long deliveryManagerId) {
    List<DeliveryRoute> deliveryRoutes = deliveryRouteRepository.findAllByDeliveryManagerId(
        deliveryManagerId);
    return deliveryRoutes.stream()
        .map(DeliveryRoute::getDeliveryId)
        .toList();
  }

  @Transactional
  public void DeleteDeliveryRoutes(UUID deliveryId, Long userId) {
    List<DeliveryRoute> routes = deliveryRouteRepository.findAllByDeliveryId(deliveryId);
    routes.forEach(deliveryRoute -> deliveryRoute.delete(userId));
  }
}
