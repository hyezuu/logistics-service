package takeoff.logistics_service.msa.slack.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import takeoff.logistics_service.msa.slack.application.dto.request.PostSlackMessageRequestDto;
import takeoff.logistics_service.msa.slack.presentation.dto.DeliveryUsers;
import takeoff.logistics_service.msa.slack.presentation.dto.StopoverHubNames;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 16.
 */
public record PostSlackRequest(@NotNull Integer orderNumber,
                               @NotNull String companyName,
                               @NotNull String productInfo,
                               @NotNull String orderRequest,
                               @NotNull String fromHubName,
                               @NotNull StopoverHubNames stopoverHubNames,
                               @NotNull String toHubName,
                               @NotNull DeliveryUsers deliveryUsers,
                               @NotNull String companyDeliveryUserName) {

    public PostSlackMessageRequestDto toApplicationDto() {
        return PostSlackMessageRequestDto.builder()
            .orderNumber(orderNumber())
            .companyName(companyName())
            .productInfo(productInfo())
            .orderRequest(orderRequest())
            .fromHubName(fromHubName())
            .stopoverHubNames(stopoverHubNames.toApplicationDto())
            .toHubName(fromHubName())
            .deliveryUsers(deliveryUsers.toApplicationDto())
            .companyDeliveryUserName(companyDeliveryUserName())
            .build();
    }

}
