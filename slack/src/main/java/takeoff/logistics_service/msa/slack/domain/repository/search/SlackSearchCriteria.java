package takeoff.logistics_service.msa.slack.domain.repository.search;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
public record SlackSearchCriteria(String message,
                                  Boolean isAsc,
                                  String sortBy,
                                  int page,
                                  int size) {

}
