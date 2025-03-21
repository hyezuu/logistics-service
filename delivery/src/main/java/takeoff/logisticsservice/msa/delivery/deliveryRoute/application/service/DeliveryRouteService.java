package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.HubClient;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.request.PostHubRouteRequestDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.response.HubRoute;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.response.PostHubRouteResponseDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.dto.request.PostDeliveryRoutesRequestDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.dto.response.GetDeliveryRouteResponseDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRoute;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.repository.DeliveryRouteRepository;

@Service
@RequiredArgsConstructor
public class DeliveryRouteService {

  private final DeliveryRouteRepository deliveryRouteRepository;
  private final HubClient hubClient;

  @Transactional
  public List<UUID> saveDeliveryRoutes(PostDeliveryRoutesRequestDto dto) {
    PostHubRouteResponseDto postHubRouteResponseDto = hubClient.postHubRoute(
        new PostHubRouteRequestDto(dto.departureHubId(), dto.destinationHubId()));

    List<HubRoute> routes = postHubRouteResponseDto.routes();

    List<DeliveryRoute> deliveryRoutes = routes.stream()
        .map(route -> DeliveryRoute.builder()
            .deliveryId(dto.deliveryId())
            .deliveryManagerId(1L) // TODO : 배송담당자 결정 로직 추가
            .sequenceNumber(1 + routes.indexOf(route))
            .fromHubId(route.fromHubId())
            .toHubId(route.toHubId())
            .estimatedDistance(route.getDistance())
            .estimatedDuration(route.getDuration())
            .build())
        .toList();

    deliveryRouteRepository.saveAll(deliveryRoutes);

    return deliveryRoutes.stream()
        .map(DeliveryRoute::getId)
        .toList();
  }

  @Transactional
  public GetDeliveryRouteResponseDto findAllDeliveryRoutesByDeliveryId(UUID deliveryId) {
    List<DeliveryRoute> deliveryRoutes = deliveryRouteRepository.findAllByDeliveryId(deliveryId);
    return GetDeliveryRouteResponseDto.of(deliveryRoutes);
  }

  @Transactional
  public void DeleteDeliveryRoutes(UUID deliveryId) {
    List<DeliveryRoute> routes = deliveryRouteRepository.findAllByDeliveryId(deliveryId);
    routes.forEach(deliveryRoute -> deliveryRoute.delete(1L));
    // TODO : 아이디 추가
  }
}
