package takeoff.logistics_service.msa.company.presentation.internal;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.company.application.service.CompanyService;
import takeoff.logistics_service.msa.company.presentation.dto.response.GetCompanyResponse;

@RestController
@RequestMapping("/api/v1/app/companies")
@RequiredArgsConstructor
public class CompanyInternalController {

	private final CompanyService companyService;

	@GetMapping("/{companyId}")
	public GetCompanyResponse findCompanyId(
			@PathVariable UUID companyId,
			@UserInfo UserInfoDto userInfoDto
	) {
		return GetCompanyResponse.from(companyService.findCompany(companyId, userInfoDto));
	}
}
