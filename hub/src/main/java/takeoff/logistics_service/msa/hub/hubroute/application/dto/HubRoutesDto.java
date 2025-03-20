package takeoff.logistics_service.msa.hub.hubroute.application.dto;

import java.util.List;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
//허브 이동경로를 리스트로 저장할 1급 컬렉션
public record HubRoutesDto(List<FindHubRoutesDto> hubRoutesDtoList) {

}
