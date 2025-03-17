package takeoff.logistics_service.msa.slack.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.presentation.dto.DeliveryUsers;
import takeoff.logistics_service.msa.slack.presentation.dto.StopoverHubNames;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
@Builder
public record PostSlackMessageRequestDto(Integer orderNumber,
                                         String companyName,
                                         String productInfo,
                                         String orderRequest,
                                         String fromHubName,
                                         StopoverHubNames stopoverHubNames,
                                         String toHubName,
                                         DeliveryUsers deliveryUsers,
                                         String companyDeliveryUserName) {

}
