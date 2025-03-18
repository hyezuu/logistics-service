package takeoff.logistics_service.msa.hub.hub.presentation.dto;

import takeoff.logistics_service.msa.hub.hub.application.dto.LocationDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */

public record LocationApi(String address,
                          Double latitude,
                          Double longitude) {

    public LocationDto toApplicationDto() {
        return LocationDto.builder()
            .address(address())
            .latitude(latitude())
            .longitude(longitude())
            .build();
    }

}
