package takeoff.logistics_service.msa.product.product.infrastructure.client.dto.response;

import java.util.List;

public record GetManagerListResponse(List<GetUserResponse> users) {
}
