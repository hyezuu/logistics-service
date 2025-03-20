package takeoff.logistics_service.msa.hub.hubroute.presentation.external;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
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
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.PutHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.service.HubRouteServiceImpl;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Distance;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Duration;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.client.external.NaverDirectionWebClient;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request.PutHubRouteRequest;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 21.
 */
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class HubRouteExternalControllerTest {

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
    void 허브_경로_조회_성공() throws Exception {
        // Given
        UUID hubRouteId = UUID.randomUUID();
        UUID fromHubId = UUID.randomUUID();
        UUID toHubId = UUID.randomUUID();
        GetHubRouteResponseDto getHubRouteResponseDto = new GetHubRouteResponseDto(
            hubRouteId,
            fromHubId,
            toHubId,
            Distance.create(123),
            Duration.create(456)
        );

        // HubRouteService mock
        when(hubRouteService.findByHubRoute(hubRouteId))
            .thenReturn(getHubRouteResponseDto);

        // When & Then
        mockMvc.perform(get("/api/v1/hubRoutes/{hubRouteId}", hubRouteId))
            .andExpect(status().isOk()) // HTTP 200 OK 응답 확인
            .andExpect(jsonPath("$.hubRouteId").value(hubRouteId.toString()))
            .andDo(document("hubRoute/find-by-hubRouteId",(
                    ResourceSnippetParameters
                        .builder()
                        .description("허브 경로를 조회합니다")
                        .tag("HubRoute-External"))
                    .pathParameters(
                    parameterWithName("hubRouteId").description("허브 경로 ID")
                ),
                responseFields(
                    fieldWithPath("hubRouteId").description("허브 경로 ID"),
                    fieldWithPath("fromHubId").description("출발 허브 ID"),
                    fieldWithPath("toHubId").description("도착 허브 ID"),
                    fieldWithPath("distance.distanceKm").description("거리 (단위: 예시)"),
                    fieldWithPath("duration.durationMinutes").description("소요 시간 (단위: 예시)")
                )
            ));

    }

    @Test
    void 허브_경로_수정_성공() throws Exception {
        // Given
        UUID hubRouteId = UUID.randomUUID();
        UUID fromHubId = UUID.randomUUID();
        UUID toHubId = UUID.randomUUID();
        Distance distance = Distance.create(123); // 예시로 Distance 생성
        Duration duration = Duration.create(456); // 예시로 Duration 생성
        PutHubRouteRequest requestDto = new PutHubRouteRequest(
            fromHubId, toHubId, distance, duration
        );

        PutHubRouteResponseDto putHubRouteResponseDto = new PutHubRouteResponseDto(
            hubRouteId,
            fromHubId,
            toHubId,
            distance,
            duration
        );

        // HubRouteService mock
        when(hubRouteService.updateHubRoute(hubRouteId, requestDto.toApplicationDto()))
            .thenReturn(putHubRouteResponseDto);

        // When & Then
        mockMvc.perform(put("/api/v1/hubRoutes/{hubRouteId}", hubRouteId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk()) // HTTP 200 OK 응답 확인

            .andDo(document("hubRoute/update-hubRoute",(
                    ResourceSnippetParameters
                        .builder()
                        .description("허브 경로를 수정합니다")
                        .tag("HubRoute-External"))
                    .pathParameters(
                    parameterWithName("hubRouteId").description("허브 경로 ID")
                ),
                requestFields(
                    fieldWithPath("fromHubId").description("출발 허브 ID"),
                    fieldWithPath("toHubId").description("도착 허브 ID"),
                    fieldWithPath("distance.distanceKm").description("거리 (단위: 예시)"),
                    fieldWithPath("duration.durationMinutes").description("소요 시간 (단위: 예시)")
                ),
                responseFields(
                    fieldWithPath("hubRouteId").description("허브 경로 ID"),
                    fieldWithPath("fromHubId").description("출발 허브 ID"),
                    fieldWithPath("toHubId").description("도착 허브 ID"),
                    fieldWithPath("distance.distanceKm").description("거리 (단위: 예시)"),
                    fieldWithPath("duration.durationMinutes").description("소요 시간 (단위: 예시)")
                )
            ));

    }

    @Test
    void 허브_경로_삭제_성공() throws Exception {
        // Given
        UUID hubRouteId = UUID.randomUUID();
        Long userId = 123L;

        // HubRouteService mock
        doNothing().when(hubRouteService).deleteHubRoute(hubRouteId, userId);  // 삭제 메서드 mock

        // When & Then
        mockMvc.perform(delete("/api/v1/hubRoutes/{hubRouteId}/{userId}", hubRouteId, userId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent()) // HTTP 204 No Content 응답 확인
            .andDo(document("hubRoute/delete-hubRoute",(
                    ResourceSnippetParameters
                        .builder()
                        .description("허브 경로를 삭제합니다")
                        .tag("HubRoute-External"))
                .pathParameters(
                    parameterWithName("hubRouteId").description("허브 경로 ID"),  // 경로 파라미터 문서화
                    parameterWithName("userId").description("사용자 ID")  // 쿼리 파라미터 문서화
                )
            ));
    }

}