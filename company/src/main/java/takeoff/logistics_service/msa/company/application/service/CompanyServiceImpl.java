package takeoff.logistics_service.msa.company.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.company.application.client.HubInternalClient;
import takeoff.logistics_service.msa.company.application.dto.request.PostCompanyRequestDto;
import takeoff.logistics_service.msa.company.application.dto.request.PutCompanyRequestDto;
import takeoff.logistics_service.msa.company.application.dto.request.SearchCompanyRequestDto;
import takeoff.logistics_service.msa.company.application.dto.response.GetCompanyResponseDto;
import takeoff.logistics_service.msa.company.application.dto.response.PaginatedResultDto;
import takeoff.logistics_service.msa.company.application.dto.response.PostCompanyResponseDto;
import takeoff.logistics_service.msa.company.application.dto.response.PutCompanyResponseDto;
import takeoff.logistics_service.msa.company.application.exception.CompanyBusinessException;
import takeoff.logistics_service.msa.company.application.exception.CompanyErrorCode;
import takeoff.logistics_service.msa.company.domain.entity.Company;
import takeoff.logistics_service.msa.company.domain.repository.CompanyRepository;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;
	private final HubInternalClient hubInternalClient;

	@Override
	public PostCompanyResponseDto saveCompany(PostCompanyRequestDto requestDto) {
		validateCompanyName(requestDto.companyName());
		validateHubExists(requestDto.hubId());

		Company company = Company.create(requestDto.toCommand());
		return PostCompanyResponseDto.from(companyRepository.save(company));
	}

	private void validateCompanyName(String companyName) {
		if(companyRepository.existsByCompanyName(companyName)){
			throw CompanyBusinessException.from(CompanyErrorCode.COMPANY_NAME_CONFLICT);
		}
	}

	private void validateHubExists(UUID hubId) {
		try {
			hubInternalClient.checkHubExists(hubId); // 호출 시 404면 예외 발생
		} catch (Exception e) {
			e.printStackTrace();
			throw CompanyBusinessException.from(CompanyErrorCode.HUB_NOT_FOUND);
		}
	}

	@Override
	@Transactional
	public PutCompanyResponseDto updateCompany(UUID companyId, PutCompanyRequestDto requestDto, UserInfoDto userInfoDto) {

		validateCompanyName(requestDto.companyName());
		validateHubExists(requestDto.hubId());

		Company company = getCompany(companyId).modify(requestDto.toCommand());
		return PutCompanyResponseDto.from(company);
	}

	private Company getCompany(UUID companyId) {
		return companyRepository.findByIdAndDeletedAtIsNull(companyId)
			.orElseThrow(() -> CompanyBusinessException.from(CompanyErrorCode.COMPANY_NOT_FOUND));
	}

	@Override
	@Transactional(readOnly = true)
	public GetCompanyResponseDto findCompany(UUID productId, UserInfoDto userInfoDto) {
		return GetCompanyResponseDto.from(getCompany(productId));
	}

	@Override
	@Transactional
	public void deleteCompany(UUID companyId, UserInfoDto userInfoDto) {
		getCompany(companyId).delete(userInfoDto.userId());
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedResultDto<GetCompanyResponseDto> searchCompany(SearchCompanyRequestDto requestDto, UserInfoDto userInfoDto) {
		return PaginatedResultDto
			.from(companyRepository.search(requestDto.toSearchCriteria()));
	}
}
