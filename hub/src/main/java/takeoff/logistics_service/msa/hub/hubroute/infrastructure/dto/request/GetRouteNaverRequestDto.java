package takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.request;

import java.util.UUID;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
public record GetRouteNaverRequestDto(UUID hubId,
                                      String hubName,
                                      String address,
                                      Double latitude,
                                      Double longitude) {

}
