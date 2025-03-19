package takeoff.logistics_service.msa.product.product.application.service;

import java.util.UUID;
import takeoff.logistics_service.msa.product.product.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.product.product.application.dto.request.PatchProductRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.request.PostProductRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.request.SearchProductRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.GetProductResponseDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PatchProductResponseDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostProductResponseDto;

public interface ProductService {

	PostProductResponseDto saveProduct(PostProductRequestDto requestDto);

	PatchProductResponseDto updateProductName(UUID id, PatchProductRequestDto applicationDto);

	GetProductResponseDto findProduct(UUID productId);

	void deleteProduct(UUID productId);

	PaginatedResultDto<GetProductResponseDto> searchProduct(SearchProductRequestDto requestDto);
}
