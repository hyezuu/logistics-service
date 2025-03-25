package takeoff.logistics_service.msa.user.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.Employee;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.HubId;
@Builder
public record GetManagerListInfoDto(
        Long userId,
        String username,
        String slackEmail,
        String role,
        UUID companyId,
        UUID hubId
) {
    public static GetManagerListInfoDto from(Employee employee) {
        CompanyId companyId = employee.getCompanyId();
        HubId hubId = employee.getHubId();

        return GetManagerListInfoDto.builder()
                .userId(employee.getId())
                .username(employee.getUsername())
                .slackEmail(employee.getSlackEmail())
                .role(employee.getRole().name())
                .companyId(companyId != null ? companyId.getCompanyIdentifier() : null)
                .hubId(hubId != null ? hubId.getHubIdentifier() : null)
                .build();
    }
}
