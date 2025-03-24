package takeoff.logistics_service.msa.order.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import takeoff.logistics_service.msa.order.application.client.dto.response.GetUserResponseDto;

@Component
@FeignClient(name = "user")
public interface UserClient {


  @GetMapping("api/v1/app/users/{userId}")
  GetUserResponseDto findByUserId(Long userId);

}
