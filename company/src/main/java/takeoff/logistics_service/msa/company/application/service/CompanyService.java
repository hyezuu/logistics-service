package takeoff.logistics_service.msa.company.application.service;

import java.util.UUID;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.company.application.dto.request.PostCompanyRequestDto;
import takeoff.logistics_service.msa.company.application.dto.request.PutCompanyRequestDto;
import takeoff.logistics_service.msa.company.application.dto.request.SearchCompanyRequestDto;
import takeoff.logistics_service.msa.company.application.dto.response.GetCompanyResponseDto;
import takeoff.logistics_service.msa.company.application.dto.response.PaginatedResultDto;
import takeoff.logistics_service.msa.company.application.dto.response.PostCompanyResponseDto;
import takeoff.logistics_service.msa.company.application.dto.response.PutCompanyResponseDto;

public interface CompanyService {

	PostCompanyResponseDto saveCompany(PostCompanyRequestDto applicationDto);

	PutCompanyResponseDto updateCompany(UUID companyId, PutCompanyRequestDto requestDto, UserInfoDto userInfoDto);

	GetCompanyResponseDto findCompany(UUID companyId, UserInfoDto userInfoDto);

	void deleteCompany(UUID companyId, UserInfoDto userInfoDto);

	PaginatedResultDto<GetCompanyResponseDto> searchCompany(SearchCompanyRequestDto applicationDto, UserInfoDto userInfoDto);
}
