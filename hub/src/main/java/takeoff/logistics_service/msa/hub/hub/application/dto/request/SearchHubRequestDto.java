package takeoff.logistics_service.msa.hub.hub.application.dto.request;

import java.util.Set;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.domain.repository.search.HubSearchCriteria;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record SearchHubRequestDto(String hubName,
                                  String address,
                                  Boolean isAsc,
                                  String sortBy,
                                  int page,
                                  int size) {
   public HubSearchCriteria toSearchCriteria() {
       return new HubSearchCriteria(
           hubName,
           address,
           isAsc,
           sortBy,
           page,
           validSize(size)
       );
   }
    private static int validSize(int size) {
        return Set.of(10, 30, 50).contains(size) ? size : 10;
    }

}
