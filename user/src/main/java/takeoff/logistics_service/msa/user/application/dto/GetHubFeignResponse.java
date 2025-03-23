package takeoff.logistics_service.msa.user.application.dto;

import java.util.UUID;

public record GetHubFeignResponse(
        UUID hubId,
        String hubName,
        String address,
        Double latitude,
        Double longitude
) {}
