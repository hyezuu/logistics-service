package takeoff.logistics_service.msa.hub.hub.model.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.hub.hub.model.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PostHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.SearchHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.SearchHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface HubRepository {

    Hub save(Hub hub);

    Optional<Hub> findById(UUID hubId);

    Page<SearchHubResponseDto> searchHub(SearchHubRequestDto requestDto, Pageable pageable);
}
