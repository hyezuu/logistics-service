package takeoff.logisticsservice.msa.delivery.delivery.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import takeoff.logisticsservice.msa.delivery.delivery.application.client.dto.response.GetUserResponseDto;

@Component
@FeignClient(name = "userDeliveryInternal")
public interface UserClient {

  GetUserResponseDto findByUserId(Long userId);

}
