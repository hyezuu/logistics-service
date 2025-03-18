package takeoff.logistics_service.msa.hub.hub.domain.repository.search;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
public record HubSearchCriteria(String hubName,
                                String address,
                                Boolean isAsc,
                                String sortBy,
                                int page,
                                int size) {

}
