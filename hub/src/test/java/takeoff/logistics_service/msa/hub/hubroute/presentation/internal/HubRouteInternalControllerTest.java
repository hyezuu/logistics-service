package takeoff.logistics_service.msa.hub.hubroute.presentation.internal;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.hub.hub.application.service.HubServiceImpl;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.DistanceDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.DurationDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.FindHubRoutesDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.HubRoutesDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.PostHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.service.HubRouteServiceImpl;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Distance;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Duration;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.HubRoute;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.client.external.NaverDirectionWebClient;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.DistanceApi;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.DurationApi;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.FindHubRoutes;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request.PostDeliveryHubRouteRequest;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request.PostHubRouteRequest;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response.PostHubRouteResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 21.
 */
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class HubRouteInternalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HubServiceImpl hubService;

    @MockitoBean
    private HubRouteServiceImpl hubRouteService;

    @MockitoBean
    private NaverDirectionWebClient naverDirectionWebClient;


    private UUID createRandomUUID(String seed) {
        return UUID.nameUUIDFromBytes(seed.getBytes());
    }


    @Test
    void 허브_경로_생성_성공() throws Exception {
        // Given
        UUID fromHubId = UUID.randomUUID();
        UUID toHubId = UUID.randomUUID();
        int distance = 123;
        int duration = 456;


        // 요청 본문 데이터 설정
        PostHubRouteRequest request = new PostHubRouteRequest(fromHubId, toHubId);

        // 응답 본문 데이터 설정
        UUID hubRouteId = UUID.randomUUID();
        PostHubRouteResponseDto responseDto = PostHubRouteResponseDto.builder()
            .hubRouteId(hubRouteId)
            .distanceDto(new DistanceDto(distance))
            .durationDto(new DurationDto(duration))
            .build();
        PostHubRouteResponse response = PostHubRouteResponse.from(responseDto);

        // HubRouteService mock 설정
        when(hubRouteService.createHubRoute(any())).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(post("/api/v1/app/hubRoutes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))  // 요청 본문 설정
            .andExpect(status().isOk())  // HTTP 201 Created 응답 확인
            .andDo(document("hubRoute/create-hubRoute",(
                    ResourceSnippetParameters
                        .builder()
                        .description("허브 경로를 생성합니다")
                        .tag("HubRoute-Internal"))
                    .requestFields(
                    fieldWithPath("fromHubId").description("출발 허브 ID"),
                    fieldWithPath("toHubId").description("도착 허브 ID")
                ),
                responseFields(
                    fieldWithPath("hubRouteId").description("허브 경로 ID"),
                    fieldWithPath("distanceApi.distance").description("거리 (단위: 예시)"),
                    fieldWithPath("durationApi.duration").description("소요 시간 (단위: 예시)")
                )
            ));
    }

    @Test
    void 배송_허브_경로_목록_조회_성공() throws Exception {
        // Given
        UUID fromHubId = UUID.randomUUID();
        UUID toHubId = UUID.randomUUID();

        // 요청 본문 데이터 설정
        PostDeliveryHubRouteRequest request = new PostDeliveryHubRouteRequest(fromHubId, toHubId);

        // 응답 본문 데이터 설정
        UUID hubRouteId = UUID.randomUUID();
        DistanceApi distanceApi = new DistanceApi(123);
        DurationApi durationApi = new DurationApi(456);


        FindHubRoutes findHubRoutes = FindHubRoutes.builder()
            .hubRouteId(hubRouteId)
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .distanceApi(distanceApi)
            .durationApi(durationApi)
            .build();

        HubRoutesDto hubRoutesDto = new HubRoutesDto(List.of(FindHubRoutesDto.from(HubRoute.builder()
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .distance(Distance.create(123))
            .duration(Duration.create(456)).build()
        )));
        // HubRouteService mock 설정
        when(hubRouteService.getDeliveryHubRouteList(any())).thenReturn(hubRoutesDto);

        // When & Then
        mockMvc.perform(post("/api/v1/app/hubRoutes/delivery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))  // 요청 본문 설정
            .andExpect(status().isOk())  // HTTP 200 OK 응답 확인
            .andDo(document("hubRoute/get-delivery-hubRouteList",(
                    ResourceSnippetParameters
                        .builder()
                        .description("허브 경로 목록을 조회합니다")
                        .tag("HubRoute-Internal"))
                    .requestFields(
                    fieldWithPath("fromHubId").description("출발 허브 ID"),
                    fieldWithPath("toHubId").description("도착 허브 ID")
                ),
                responseFields(
                    fieldWithPath("hubAllListResponseList[].hubRouteId").description("허브 경로 ID"),
                    fieldWithPath("hubAllListResponseList[].fromHubId").description("출발 허브 ID"),
                    fieldWithPath("hubAllListResponseList[].toHubId").description("도착 허브 ID"),
                    fieldWithPath("hubAllListResponseList[].distanceApi.distance").description("거리 (단위: 예시)"),
                    fieldWithPath("hubAllListResponseList[].durationApi.duration").description("소요 시간 (단위: 예시)")
                )
            ));
    }


}