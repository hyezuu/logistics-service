package takeoff.logistics_service.msa.hub.hub.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    public static Location create(String address, Double latitude, Double longitude) {
        return new Location(address, latitude, longitude);
    }

    private Location(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private void validateLatitude(double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("위도(latitude)는 -90 ~ 90 범위여야 합니다.");
        }
    }

    private void validateLongitude(double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("경도(longitude)는 -180 ~ 180 범위여야 합니다.");
        }
    }

    private void validateNotEmpty(String address) {
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException(address + "는 비워둘 수 없습니다.");
        }
    }


}
