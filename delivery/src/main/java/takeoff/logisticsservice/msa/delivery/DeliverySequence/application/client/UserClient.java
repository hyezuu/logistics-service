package takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client.response.GetDeliveryManagerListInternalResponseDto;

@Component
@FeignClient(name = "user")
public interface UserClient {

  @GetMapping("/api/v1/app/delivery-managers/company")
  GetDeliveryManagerListInternalResponseDto findAllCompanyDeliveryManagerByHubId(
      @RequestParam UUID hubId
  );

  @GetMapping("/api/v1/app/delivery-managers/hub")
  GetDeliveryManagerListInternalResponseDto findAllHubDeliveryManager();

}
