package takeoff.logistics_service.msa.hub.hub.application.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.PaginatedResultDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 24.
 */
@Builder
public record PaginatedResultHubResponseDto(PaginatedResultDtoList searchHubList) {

    public static PaginatedResultHubResponseDto from(PaginatedResultDto<SearchHubResponseDto> searchHubList) {
        return PaginatedResultHubResponseDto.builder()
            .searchHubList(PaginatedResultDtoList.from(searchHubList))
            .build();
    }

}
