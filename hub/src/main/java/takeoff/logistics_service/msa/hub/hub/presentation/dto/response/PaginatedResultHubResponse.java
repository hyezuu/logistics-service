package takeoff.logistics_service.msa.hub.hub.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PaginatedResultHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.PaginatedResultApi;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 24.
 */
@Builder
public record PaginatedResultHubResponse(PaginatedResultApi<SearchHubResponse> searchHubList) {

    public static PaginatedResultHubResponse from(PaginatedResultHubResponseDto paginatedResultHubResponseDto) {
        return PaginatedResultHubResponse.builder()
            .searchHubList(new PaginatedResultApi<>(
                paginatedResultHubResponseDto.searchHubList().responseDtoPaginatedResultDto().content().stream()
                    .map(SearchHubResponse::from)
                    .toList(),
                paginatedResultHubResponseDto.searchHubList().responseDtoPaginatedResultDto().page(),
                paginatedResultHubResponseDto.searchHubList().responseDtoPaginatedResultDto().size(),
                paginatedResultHubResponseDto.searchHubList().responseDtoPaginatedResultDto().totalElements(),
                paginatedResultHubResponseDto.searchHubList().responseDtoPaginatedResultDto().totalPages()
            )).build();
    }


}
