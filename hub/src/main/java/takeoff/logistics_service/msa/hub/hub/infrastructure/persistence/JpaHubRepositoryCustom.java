package takeoff.logistics_service.msa.hub.hub.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.SearchHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.SearchHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface JpaHubRepositoryCustom {
    Page<SearchHubResponseDto> searchHub(SearchHubRequestDto requestDto, Pageable pageable);

}
