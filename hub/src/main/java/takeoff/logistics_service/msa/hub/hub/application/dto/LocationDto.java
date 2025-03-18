package takeoff.logistics_service.msa.hub.hub.application.dto;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.domain.entity.Location;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record LocationDto(String address,
                          Double latitude,
                          Double longitude) {

    public Location toVo() {
        return Location.create(address, latitude, longitude);
    }

}
