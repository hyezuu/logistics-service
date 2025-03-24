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

    //캐싱에 키를 조건에 따라 캐싱하기 위해 설정한 메서드
    //ex) hubs::page:1-size:10-sort:desc-hubName:테스트-address:테스트
//    public String toCacheKey() {
//        return String.format("page:%d-size:%d-sort:%s-hubName:%s-address:%s",
//            this.page,
//            this.size,
//            this.sortBy,
//            this.hubName != null ? this.hubName : "none",
//            this.address != null ? this.address : "none"
//        );
//    }
}
