package takeoff.logistics_service.msa.order.application.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import takeoff.logistics_service.msa.order.application.client.dto.request.AbortStockRequestDto;
import takeoff.logistics_service.msa.order.application.client.dto.request.PrePareStockRequestDto;
import takeoff.logistics_service.msa.order.application.client.dto.response.GetStockResponseDto;

@Component
@FeignClient(name = "product")
public interface StockClient {

  @PostMapping("/api/v1/app/stock/prepare")
  void prepareStock(@RequestBody PrePareStockRequestDto dto);

  @PostMapping("/api/v1/app/stock/abort")
  void abortStock(@RequestBody AbortStockRequestDto dto);

  @GetMapping("/api/v1/app/stock")
  GetStockResponseDto getStock(@RequestParam(name = "productId") UUID productId);
}
