package takeoff.logistics_service.msa.company.presentation.external;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.company.application.service.CompanyService;
import takeoff.logistics_service.msa.company.presentation.dto.request.PutCompanyRequest;
import takeoff.logistics_service.msa.company.presentation.dto.request.PostCompanyRequest;
import takeoff.logistics_service.msa.company.presentation.dto.request.SearchCompanyRequest;
import takeoff.logistics_service.msa.company.presentation.dto.response.GetCompanyResponse;
import takeoff.logistics_service.msa.company.presentation.dto.response.PaginatedResultApi;
import takeoff.logistics_service.msa.company.presentation.dto.response.PutCompanyResponse;
import takeoff.logistics_service.msa.company.presentation.dto.response.PostCompanyResponse;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyExternalController {

	private final CompanyService companyService;

	@PostMapping
	public ResponseEntity<PostCompanyResponse> saveCompany(
		@Valid @RequestBody PostCompanyRequest requestDto) {

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(PostCompanyResponse
				.from(companyService.saveCompany(requestDto.toApplicationDto())));
	}

	@PutMapping("/{companyId}")
	public ResponseEntity<PutCompanyResponse> updateCompany(
		@PathVariable UUID companyId, @Valid @RequestBody PutCompanyRequest requestDto) {

		return ResponseEntity.ok(PutCompanyResponse
			.from(companyService.updateCompany(companyId, requestDto.toApplicationDto())));
	}

	@GetMapping("/{companyId}")
	public ResponseEntity<GetCompanyResponse> findCompanyId(@PathVariable UUID companyId) {

		return ResponseEntity.ok(GetCompanyResponse.from(companyService.findCompany(companyId)));
	}

	@DeleteMapping("/{companyId}")
	public ResponseEntity<Void> deleteCompany(@PathVariable UUID companyId) {

		companyService.deleteCompany(companyId, 0L);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<PaginatedResultApi<GetCompanyResponse>> searchCompany(
		@ModelAttribute SearchCompanyRequest requestDto) {

		return ResponseEntity.ok(PaginatedResultApi.from(
			companyService.searchCompany(requestDto.toApplicationDto())));
	}
}
