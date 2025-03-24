package takeoff.logistics_service.msa.auth.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import takeoff.logistics_service.msa.auth.application.dto.request.UserValidationRequestDto;
import takeoff.logistics_service.msa.auth.application.dto.response.UserValidationResponseDto;
import takeoff.logistics_service.msa.common.config.FeignClientConfig;

@FeignClient(name = "user", path = "/api/v1/app/users", configuration = FeignClientConfig.class)
public interface UserServiceFeignClient {
    @PostMapping("/validate")
    UserValidationResponseDto validateUser(@RequestBody UserValidationRequestDto requestDto);
}
