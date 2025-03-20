package takeoff.logistics_service.msa.hub.hubroute.application.dto;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Builder
public record HubAllListResponseDto(UUID hubId,
                                    String hubName,
                                    String address,
                                    Double latitude,
                                    Double longitude) {

}
