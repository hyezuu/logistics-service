package takeoff.logistics_service.msa.product.stock.application.service;

import java.util.UUID;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.product.stock.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.AbortStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.DecreaseStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.IncreaseStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.PrepareStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.SearchStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.StockIdRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.DecreaseStockResponseDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.GetStockResponseDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.IncreaseStockResponseDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.PostStockResponseDto;

public interface StockService {

	PostStockResponseDto saveStock(PostStockRequestDto requestDto, UserInfoDto userInfo);

	IncreaseStockResponseDto increaseStock(IncreaseStockRequestDto requestDto, UserInfoDto userInfo);

	DecreaseStockResponseDto decreaseStock(DecreaseStockRequestDto requestDto, UserInfoDto userInfo);

	void delete(StockIdRequestDto requestDto, UserInfoDto userInfo);

	GetStockResponseDto findStock(StockIdRequestDto requestDto);

	void prepareStock(PrepareStockRequestDto requestDto);

	void abortStock(AbortStockRequestDto requestDto);

	PaginatedResultDto<GetStockResponseDto> searchStock(SearchStockRequestDto requestDto);

	void deleteAllByProductId(UUID productId, UserInfoDto userInfo);

	void deleteAllByHubId(UUID hubId, UserInfoDto userInfo);

	GetStockResponseDto findStockWithProductId(UUID productId);
}
