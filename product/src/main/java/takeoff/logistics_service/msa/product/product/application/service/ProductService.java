package takeoff.logistics_service.msa.product.product.application.service;

import java.util.UUID;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.product.product.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.product.product.application.dto.request.PatchProductRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.request.PostProductRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.request.SearchProductRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.GetProductResponseDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PatchProductResponseDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostProductResponseDto;

public interface ProductService {

	PostProductResponseDto saveProduct(PostProductRequestDto requestDto, UserInfoDto userInfo);

	PatchProductResponseDto updateProductName(
		UUID id, PatchProductRequestDto applicationDto, UserInfoDto userInfo);

	GetProductResponseDto findProduct(UUID productId);

	void deleteProduct(UUID productId, UserInfoDto userInfo);

	PaginatedResultDto<GetProductResponseDto> searchProduct(SearchProductRequestDto requestDto);
}
