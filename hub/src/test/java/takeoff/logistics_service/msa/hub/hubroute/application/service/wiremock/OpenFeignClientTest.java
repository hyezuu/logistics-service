package takeoff.logistics_service.msa.hub.hubroute.application.service.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetHubRouteNaverResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.service.HubRouteServiceImpl;
import takeoff.logistics_service.msa.hub.hubroute.domain.repository.HubRouteRepository;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.client.external.NaverDirectionWebClient;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.client.feign.FeignHubClient;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.request.HubIds;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.response.GetRouteResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 20.
 */
@DisplayName("허브 이동경로 생성 WireMock Test")
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureWireMock(port = 0)
@SpringBootTest(
    properties = "eureka.client.enabled=false"
)
public class OpenFeignClientTest {

    @Autowired
    WireMockServer wireMockServer;
    @MockitoBean
    HubRouteServiceImpl hubRouteService;
    @MockitoBean
    NaverDirectionWebClient naverDirectionWebClient;
    @MockitoBean
    HubRouteRepository hubRouteRepository;

    @MockitoBean
    FeignHubClient feignHubClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
    }

    @Test
    void mockServerRunTest() {
        System.out.println("mock 서버 실행");
    }


    @DisplayName("허브 이동경로 생성 Feign Client Stub")
    @Test
    void stub_HubClient_Response() throws IOException {
        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put("hubId", "1234-5678-9101");
        expectedBody.put("hubName", "Test Hub");
        expectedBody.put("address", "Test Address");
        expectedBody.put("latitude", "12.34");
        expectedBody.put("longitude", "56.78");

        String responseBody = objectMapper.writeValueAsString(expectedBody);

        // WireMock 스텁을 설정하여 mock 응답
        wireMockServer.stubFor(post(urlEqualTo("/api/v1/app/hubs/stopover"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(responseBody)));

        UUID fromHubId = UUID.randomUUID();
        UUID toHubId = UUID.randomUUID();
        HubIds hubIds = new HubIds(fromHubId, toHubId);

        UUID test = UUID.randomUUID();
        List<GetRouteResponseDto> mockRouteResponse = List.of(new GetRouteResponseDto(
            test, "Test Hub", "Test Address", 12.34, 56.78));

        when(feignHubClient.findByToHubIdAndFromHubId(hubIds)).thenReturn(mockRouteResponse.stream().map(GetRouteResponse::fromTest).toList());

        wireMockServer.stubFor(post(urlEqualTo("/api/v1/app/hubs/stopover"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody(responseBody)));

        // 응답 검증 (Feign 클라이언트가 실제로 요청을 보냈는지 확인)
        List<GetRouteResponse> response = feignHubClient.findByToHubIdAndFromHubId(hubIds);
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Test Hub", response.get(0).hubName());
    }

    @DisplayName("허브 이동경로 생성 NAVER API Stub")
    @Test
    void stub_NaverApi_Response() throws IOException {
        GetHubRouteNaverResponseDto result = new GetHubRouteNaverResponseDto(123,456);

        String responseBody = objectMapper.writeValueAsString(result);

        //WireMock은 헤더 필요 없음
        wireMockServer.stubFor(get(urlPathEqualTo("/map-direction/v1/driving"))
            .withQueryParam("goal", equalTo("12.34,56.78"))
            .withQueryParam("start", equalTo("12.34,56.78"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(responseBody)));

        when(naverDirectionWebClient.sendRequestToNaver(any())).thenReturn(Mono.just(result));

        GetHubRouteNaverResponseDto response = naverDirectionWebClient.sendRequestToNaver(any()).block();
        assertNotNull(response);
        assertEquals(123, response.distance());
    }


}
