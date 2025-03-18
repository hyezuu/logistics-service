package takeoff.logistics_service.msa.slack.presentation.dto;

import java.util.List;
import takeoff.logistics_service.msa.slack.application.dto.DeliveryUsersDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 16.
 */
public record DeliveryUsers(List<String> deliveryUserNames) {

    public DeliveryUsersDto toApplicationDto() {
        return DeliveryUsersDto.builder()
            .deliveryUserNames(deliveryUserNames())
            .build();
    }

}
