package takeoff.logistics_service.msa.product.product.infrastructure.client.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetCompanyResponse(
	UUID companyId, String companyName, String companyType,
	UUID hubId, String address, LocalDateTime createdAt, LocalDateTime updatedAt) {

}
