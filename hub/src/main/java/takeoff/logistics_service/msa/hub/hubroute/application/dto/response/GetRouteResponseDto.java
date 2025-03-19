package takeoff.logistics_service.msa.hub.hubroute.application.dto.response;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Builder
public record GetRouteResponseDto(UUID hubId,
                                  String hubName,
                                  String address,
                                  Double latitude,
                                  Double longitude) {

}
