package takeoff.logistics_service.msa.order.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.order.application.client.dto.response.GetUserResponseDto;

@Component
@FeignClient(name = "user")
public interface UserClient {

  GetUserResponseDto findByUserId(Long userId);

}
