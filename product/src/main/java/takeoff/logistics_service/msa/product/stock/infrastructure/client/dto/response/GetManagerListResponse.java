package takeoff.logistics_service.msa.product.stock.infrastructure.client.dto.response;

import java.util.List;

public record GetManagerListResponse(List<GetUserResponse> users) {
}
