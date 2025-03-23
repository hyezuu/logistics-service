package takeoff.logistics_service.msa.user.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetCompanyFeignResponse(
        UUID companyId,
        String companyName,
        String companyType,
        UUID hubId,
        String address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
