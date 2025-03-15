package takeoff.logistics_service.msa.slack.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.slack.presentation.dto.request.SearchSlackRequestDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SearchSlackResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 14.
 */
public interface JpaSlackRepositoryCustom {
    Page<SearchSlackResponseDto> searchSlack(SearchSlackRequestDto searchSlackRequestDto, Pageable pageable);

}
