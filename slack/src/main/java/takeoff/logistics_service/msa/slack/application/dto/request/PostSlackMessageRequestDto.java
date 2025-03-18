package takeoff.logistics_service.msa.slack.application.dto.request;

import lombok.Builder;
import takeoff.logistics_service.msa.slack.application.dto.DeliveryUsersDto;
import takeoff.logistics_service.msa.slack.application.dto.StopoverHubNamesDto;

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
                                         StopoverHubNamesDto stopoverHubNames,
                                         String toHubName,
                                         DeliveryUsersDto deliveryUsers,
                                         String companyDeliveryUserName) {

}
