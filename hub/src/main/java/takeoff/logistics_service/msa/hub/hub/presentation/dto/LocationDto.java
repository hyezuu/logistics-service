package takeoff.logistics_service.msa.hub.hub.presentation.dto;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.model.entity.Location;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */

public record LocationDto(String address,
                          Double latitude,
                          Double longitude) {

    public Location toVo() {
        return Location.create(address, latitude, longitude);
    }

}
