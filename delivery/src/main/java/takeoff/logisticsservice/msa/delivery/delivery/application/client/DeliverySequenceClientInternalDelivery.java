package takeoff.logisticsservice.msa.delivery.delivery.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import takeoff.logisticsservice.msa.delivery.delivery.application.client.dto.request.GetCompanyDeliverySequenceRequestDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.client.dto.response.GetCompanyDeliverySequenceResponseDto;

@Component
@FeignClient(name = "deliverySequenceInternalDelivery")
public interface DeliverySequenceClientInternalDelivery {

  @GetMapping("/api/v1/app/deliveryRoutes/nextCompanyDeliverySequence")
  GetCompanyDeliverySequenceResponseDto findNextCompanyDeliverySequence(
      GetCompanyDeliverySequenceRequestDto dto
  );
}
