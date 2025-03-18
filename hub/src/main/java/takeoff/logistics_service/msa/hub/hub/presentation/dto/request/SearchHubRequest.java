package takeoff.logistics_service.msa.hub.hub.presentation.dto.request;

import takeoff.logistics_service.msa.hub.hub.application.dto.request.SearchHubRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */

public record SearchHubRequest(String hubName,
                               String address,
                               Boolean isAsc,
                               String sortBy,
                               int page,
                               int size) {

    public SearchHubRequestDto toApplicationDto() {
        return SearchHubRequestDto.builder()
            .hubName(hubName)
            .address(address)
            .isAsc(isAsc)
            .sortBy(sortBy)
            .page(page)
            .size(size)
            .build();
    }

}
