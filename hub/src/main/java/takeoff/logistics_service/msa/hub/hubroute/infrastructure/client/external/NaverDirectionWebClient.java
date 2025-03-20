package takeoff.logistics_service.msa.hub.hubroute.infrastructure.client.external;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetHubRouteNaverResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.exception.HubRouteBusinessException;
import takeoff.logistics_service.msa.hub.hubroute.application.exception.HubRouteErrorCode;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.response.GetHubRouteNaverResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class NaverDirectionWebClient implements NaverApiAdapter{

    private final WebClient webClient;

    @Value("${naver.id}")
    private String apiId;
    @Value("${naver.key}")
    private String apiKey;
    @Value("${naver.url}")
    private String naverUrl;

    private static final String HEADER_ID = "X-NCP-APIGW-API-KEY-ID";
    private static final String HEADER_KEY = "X-NCP-APIGW-API-KEY";

    public Mono<GetHubRouteNaverResponseDto> sendRequestToNaver(List<GetRouteResponseDto> responseToHub) {
        if (responseToHub == null || responseToHub.size() < 2) {
            return Mono.error(HubRouteBusinessException.from(HubRouteErrorCode.HUB_ROUTE_NOT_FOUND));
        }

        String requestUrl = buildRequestUrl(responseToHub.get(0), responseToHub.get(responseToHub.size() - 1));

        return webClient.mutate()
            .build().method(HttpMethod.GET)
            .uri(requestUrl)
            .headers(httpHeaders -> {
                httpHeaders.set(HEADER_ID, apiId);
                httpHeaders.set(HEADER_KEY,apiKey);})
            .retrieve()
            .bodyToMono(NaverApiResponse.class)
            .map(this::convertToHubRouteResponse)
            .map(GetHubRouteNaverResponse::from);


    }

    private String buildRequestUrl(GetRouteResponseDto startHub, GetRouteResponseDto goalHub) {
        return UriComponentsBuilder.fromUriString(naverUrl)
            .queryParam("goal", goalHub.longitude() + "," + goalHub.latitude())
            .queryParam("start", startHub.longitude() + "," + startHub.latitude())
            .toUriString();
    }

    private GetHubRouteNaverResponse convertToHubRouteResponse(NaverApiResponse response) {
        if (response == null || response.route() == null || response.route().traoptimal().isEmpty()) {
            throw HubRouteBusinessException.from(HubRouteErrorCode.NAVER_ERROR);
        }
        return getResult(response);
    }
    private static GetHubRouteNaverResponse getResult(NaverApiResponse response) {
        int totalDistance = response.route().traoptimal().get(0).summary().distance() / 1000;
        int totalDuration = response.route().traoptimal().get(0).summary().duration() / 60;


        return new GetHubRouteNaverResponse(totalDistance, totalDuration);
    }



}
