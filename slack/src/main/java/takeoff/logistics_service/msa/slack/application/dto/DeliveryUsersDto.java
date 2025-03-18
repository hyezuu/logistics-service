package takeoff.logistics_service.msa.slack.application.dto;

import java.util.List;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 16.
 */
@Builder
public record DeliveryUsersDto(List<String> deliveryUserNames) {

}
