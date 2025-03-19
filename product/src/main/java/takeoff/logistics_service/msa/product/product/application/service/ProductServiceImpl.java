package takeoff.logistics_service.msa.product.product.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.product.product.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.product.product.application.dto.request.PatchProductRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.request.PostProductRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.request.SearchProductRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.GetProductResponseDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PatchProductResponseDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostProductResponseDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostStockResponseDto;
import takeoff.logistics_service.msa.product.product.application.exception.ProductBusinessException;
import takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode;
import takeoff.logistics_service.msa.product.product.domain.entity.Product;
import takeoff.logistics_service.msa.product.product.domain.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final StockClient stockClient;

	@Override
	@Transactional
	public PostProductResponseDto saveProduct(PostProductRequestDto requestDto) {

		Product product = productRepository
			.save(Product.create(requestDto.toCommand()));
		PostStockResponseDto responseDto = stockClient
			.saveStock(PostStockRequestDto.from(product.getId(), requestDto));

		return PostProductResponseDto.from(product, responseDto);
	}

	@Override
	@Transactional(readOnly = true)
	public GetProductResponseDto findProduct(UUID productId) {
		return GetProductResponseDto.from(getProduct(productId));
	}

	private Product getProduct(UUID productId) {
		return productRepository
			.findByIdAndDeletedAtIsNull(productId)
			.orElseThrow(() -> ProductBusinessException.from(ProductErrorCode.PRODUCT_NOT_FOUND));
	}

	@Override
	@Transactional
	public PatchProductResponseDto updateProductName(
		UUID productId, PatchProductRequestDto requestDto) {

		Product product = getProduct(productId);
		product.modify(requestDto.toCommand());
		return PatchProductResponseDto.from(product);
	}

	@Override
	@Transactional
	public void deleteProduct(UUID productId) {
		getProduct(productId).delete(0L);
		stockClient.deleteStock(productId);
	}

	@Override
	public PaginatedResultDto<GetProductResponseDto> searchProduct(
		SearchProductRequestDto requestDto) {
		return PaginatedResultDto
			.from(productRepository.search(requestDto.toSearchCriteria()));
	}
}
