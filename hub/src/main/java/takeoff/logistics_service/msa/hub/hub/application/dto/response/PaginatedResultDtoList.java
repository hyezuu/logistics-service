package takeoff.logistics_service.msa.hub.hub.application.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.PaginatedResultDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 24.
 */
@Builder
public record PaginatedResultDtoList(PaginatedResultDto<SearchHubResponseDto> responseDtoPaginatedResultDto) {

    public static PaginatedResultDtoList from(PaginatedResultDto<SearchHubResponseDto> searchHubList) {
        return PaginatedResultDtoList.builder()
            .responseDtoPaginatedResultDto(searchHubList)
            .build();
    }
}
