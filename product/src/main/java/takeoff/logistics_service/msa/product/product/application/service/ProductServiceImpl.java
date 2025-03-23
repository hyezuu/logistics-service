package takeoff.logistics_service.msa.product.product.application.service;

import static takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode.ACCESS_DENIED;
import static takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode.PRODUCT_NOT_FOUND;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
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
import takeoff.logistics_service.msa.product.product.domain.entity.Product;
import takeoff.logistics_service.msa.product.product.domain.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final StockClient stockClient;
	private final HubClient hubClient;
	private final CompanyClient companyClient;
	private final UserClient userClient;

	@Override
	@Transactional
	public PostProductResponseDto saveProduct(
		PostProductRequestDto requestDto, UserInfoDto userInfo) {

		validateRequest(requestDto.hubId(), requestDto.companyId());
		validateAccess(requestDto.companyId(), userInfo);
		Product savedProduct = getSavedProduct(requestDto);
		PostStockResponseDto savedStock = getSavedStock(requestDto, savedProduct);
		return PostProductResponseDto.from(savedProduct, savedStock);
	}

	private void validateRequest(UUID hubId, UUID companyId) {
		hubClient.findByHubId(hubId);
		companyClient.findByCompanyId(companyId);
	}

	private void validateAccess(UUID resourceId, UserInfoDto userInfo) {
		if (userInfo.isCompanyManager() && !getCompanyId(userInfo).equals(resourceId)) {
			throw ProductBusinessException.from(ACCESS_DENIED);
		}
	}

	private Product getSavedProduct(PostProductRequestDto requestDto) {
		return productRepository.save(Product.create(requestDto.toCommand()));
	}

	private PostStockResponseDto getSavedStock(
		PostProductRequestDto requestDto, Product savedProduct) {
		return stockClient.saveStock(
			PostStockRequestDto.from(savedProduct.getId(), requestDto));
	}

	private UUID getCompanyId(UserInfoDto userInfo) {
		return userClient.findByUserId(userInfo.userId()).companyId();
	}

	@Override
	@Transactional(readOnly = true)
	public GetProductResponseDto findProduct(UUID productId) {
		return GetProductResponseDto.from(getProduct(productId));
	}

	private Product getProduct(UUID productId) {
		return productRepository
			.findByIdAndDeletedAtIsNull(productId)
			.orElseThrow(() -> ProductBusinessException.from(PRODUCT_NOT_FOUND));
	}

	@Override
	@Transactional
	public PatchProductResponseDto updateProductName(
		UUID productId, PatchProductRequestDto requestDto, UserInfoDto userInfo) {

		Product product = getProduct(productId);
		validateAccess(product.getCompanyId(), userInfo);
		return PatchProductResponseDto.from(product.modify(requestDto.toCommand()));
	}

	@Override
	@Transactional
	public void deleteProduct(UUID productId, UserInfoDto userInfo) {
		Product product = getProduct(productId);
		validateAccess(product.getCompanyId(), userInfo);
		product.delete(userInfo.userId());
		stockClient.deleteStock(productId);
	}

	@Override
	public PaginatedResultDto<GetProductResponseDto> searchProduct(
		SearchProductRequestDto requestDto) {
		return PaginatedResultDto.from(
			productRepository.search(requestDto.toSearchCriteria()));
	}
}
