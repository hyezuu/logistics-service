package takeoff.logistics_service.msa.order.application.client.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetCompanyResponseDto(
    UUID companyId, String companyName, String companyType,
    UUID hubId, String address, LocalDateTime createdAt, LocalDateTime updatedAt) {

}
