package takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client;

import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client.response.GetCompanyDeliveryManagerResponseDto;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client.response.GetHubDeliveryManagerResponseDto;

@Component
@FeignClient(name = "user")
public interface UserClient {

  @GetMapping("/api/v1/users")
  List<GetCompanyDeliveryManagerResponseDto> findAllCompanyDeliveryManagerByHubId(UUID hubId);

  @GetMapping("/api/v1/users")
  List<GetHubDeliveryManagerResponseDto> findAllHubDeliveryManager();

}
