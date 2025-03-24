package takeoff.logisticsservice.msa.delivery.delivery.application;

import static takeoff.logistics_service.msa.common.domain.UserRole.COMPANY_DELIVERY_MANAGER;
import static takeoff.logistics_service.msa.common.domain.UserRole.COMPANY_MANAGER;
import static takeoff.logistics_service.msa.common.domain.UserRole.HUB_MANAGER;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;
import takeoff.logisticsservice.msa.delivery.delivery.application.client.DeliverySequenceClientInternalDelivery;
import takeoff.logisticsservice.msa.delivery.delivery.application.client.UserClient;
import takeoff.logisticsservice.msa.delivery.delivery.application.client.dto.request.GetCompanyDeliverySequenceRequestDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.PaginatedResultDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.request.SearchDeliveryRequestDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.response.SearchDeliveryResponseDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.exception.DeliveryBusinessException;
import takeoff.logisticsservice.msa.delivery.delivery.application.exception.DeliveryErrorCode;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.Delivery;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.DeliveryId;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.DeliveryRepository;
import takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.request.PatchDeliveryRequestDto;
import takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.request.PostDeliveryRequestDto;


@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final DeliverySequenceClientInternalDelivery deliverySequenceClient;
  private final UserClient userClient;


  // TODO : 분산 트랜잭션
  @Transactional
  public UUID saveDelivery(PostDeliveryRequestDto dto) {

    Long deliveryManagerId = deliverySequenceClient.findNextCompanyDeliverySequence(
        new GetCompanyDeliverySequenceRequestDto(
            dto.toHubId()
        )).companyDeliveryManagerId();

    Delivery delivery = Delivery.builder()
        .orderId(dto.orderID())
        .deliveryManagerId(deliveryManagerId)
        .customerId(dto.customerId())
        .fromHubId(dto.fromHubId())
        .toHubId(dto.toHubId())
        .build();

    deliveryRepository.save(delivery);

    return delivery.getId().getId();
  }

  @Transactional
  public void updateDeliveryStatus(PatchDeliveryRequestDto dto, Long userId, UserRole userRole) {
    Delivery delivery = deliveryRepository.findById(DeliveryId.from(dto.deliveryId()))
        .orElseThrow(() -> DeliveryBusinessException.from(DeliveryErrorCode.DELIVERY_NOT_FOUND));

    if (userRole.equals(HUB_MANAGER)) {
      validateHubManagerAccess(delivery.getToHubId(), userId);
    }

    if (userRole.equals(COMPANY_DELIVERY_MANAGER)) {
      validateDeliveryManagerAccess(delivery.getDeliveryManagerId(), userId);
    }

    try {
      delivery.modifyStatus(dto.status());
    } catch (IllegalArgumentException e) {
      throw DeliveryBusinessException.from(DeliveryErrorCode.INVALID_DELIVERY_STATUS);
    }
  }

  @Transactional
  public void deleteDelivery(UUID deliveryId, Long userId, UserRole userRole) {
    Delivery delivery = deliveryRepository.findById(DeliveryId.from(deliveryId))
        .orElseThrow(() -> DeliveryBusinessException.from(DeliveryErrorCode.DELIVERY_NOT_FOUND));

    if (userRole.equals(HUB_MANAGER)) {
      validateHubManagerAccess(delivery.getToHubId(), userId);
    }

    delivery.delete(userId);
  }

  @Transactional
  public PaginatedResultDto<SearchDeliveryResponseDto> searchDelivery(
      SearchDeliveryRequestDto dto,
      Long userId,
      UserRole userRole
  ) {
    return switch (userRole) {
      case MASTER_ADMIN -> PaginatedResultDto.from(deliveryRepository.findAllBySearchParams(
          dto.toSearchCriteria()
      ));
      case HUB_MANAGER -> PaginatedResultDto.from(deliveryRepository.findAllBySearchParams(
          dto.toSearchCriteria(getHubId(userId), null, null)
      ));
      case COMPANY_MANAGER -> PaginatedResultDto.from(deliveryRepository.findAllBySearchParams(
          dto.toSearchCriteria(null, userId, null)
      ));
      case HUB_DELIVERY_MANAGER, COMPANY_DELIVERY_MANAGER ->
          PaginatedResultDto.from(deliveryRepository.findAllBySearchParams(
              dto.toSearchCriteria(null, null, userId)
          ));
    };
  }

  @Transactional
  public List<UUID> findAllDeliveryIdByUser(Long userId) {
    List<Delivery> deliveries = deliveryRepository.findAllByDeliveryManagerId(userId);

    return deliveries.stream()
        .map(Delivery::getIdLiteral)
        .toList();
  }


  private void validateHubManagerAccess(UUID resourceId, Long userId) {
    if (!getHubId(userId).equals(resourceId)) {
      throw BusinessException.from(CommonErrorCode.FORBIDDEN);
    }
  }

  private void validateDeliveryManagerAccess(Long resourceId, Long userId) {
    if (!resourceId.equals(userId)) {
      throw BusinessException.from(CommonErrorCode.FORBIDDEN);
    }

  }

  private void validateCompanyManagerAccess(UUID resourceId, Long userId, UserRole userRole) {
    if (userRole.equals(COMPANY_MANAGER) && !getCompanyId(userId).equals(resourceId)) {
      throw BusinessException.from(CommonErrorCode.FORBIDDEN);
    }
  }

  private UUID getHubId(Long userId) {
    return userClient.findByUserId(userId).hubId();
  }

  private UUID getCompanyId(Long userId) {
    return userClient.findByUserId(userId).companyId();
  }
}
