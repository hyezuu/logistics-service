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

    public static PostSlackMessageRequestDto toApplicationDto(PostSlackRequest request) {
        return PostSlackMessageRequestDto.builder()
            .orderNumber(request.orderNumber())
            .companyName(request.companyName())
            .productInfo(request.productInfo())
            .orderRequest(request.orderRequest())
            .fromHubName(request.fromHubName())
            .stopoverHubNames(StopoverHubNames.from(request.stopoverHubNames()))
            .toHubName(request.fromHubName())
            .deliveryUsers(DeliveryUsers.from(request.deliveryUsers()))
            .companyDeliveryUserName(request.companyDeliveryUserName())
            .build();
    }

}
