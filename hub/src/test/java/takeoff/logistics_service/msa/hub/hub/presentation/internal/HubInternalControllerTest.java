package takeoff.logistics_service.msa.hub.hub.presentation.internal;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
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
import takeoff.logistics_service.msa.hub.hub.application.dto.feign.GetAllHubsDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.GetHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.GetRouteResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.service.HubServiceImpl;
import takeoff.logistics_service.msa.hub.hubroute.application.service.HubRouteServiceImpl;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.client.external.NaverDirectionWebClient;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.request.HubIds;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 21.
 */
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class HubInternalControllerTest {


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
    void 허브_아이디로_허브_조회_성공() throws Exception {
        // Given
        UUID hubId = UUID.randomUUID();

        // Mock된 서비스에서 반환할 데이터 생성
        GetHubResponseDto getHubResponseDto = new GetHubResponseDto(
            hubId,
            "Test Hub",
            "Test Address",
            37.7749,
            70.4194
        );

        // HubService mock
        when(hubService.findByHubId(hubId)).thenReturn(getHubResponseDto);

        // When & Then
        mockMvc.perform(get("/api/v1/app/hubs/{hubId}", hubId))
            .andExpect(status().isOk()) // HTTP 200 OK 응답 확인
            .andDo(document("hub/find-hub-by-id",(
                    ResourceSnippetParameters
                        .builder()
                        .description("허브를 조회합니다")
                        .tag("Hub-Internal"))
                    .pathParameters(
                    parameterWithName("hubId").description("허브의 고유 ID")
                ),
                responseFields(
                    fieldWithPath("hubId").description("허브 ID"),
                    fieldWithPath("hubName").description("허브 이름"),
                    fieldWithPath("address").description("허브 주소"),
                    fieldWithPath("latitude").description("허브 위도"),
                    fieldWithPath("longitude").description("허브 경도")
                )
            ));

    }

    @Test
    void 출발지와_도착지_허브_아이디로_경유지_조회_성공() throws Exception {
        // Given
        UUID fromHubId = UUID.randomUUID();
        UUID toHubId = UUID.randomUUID();
        HubIds hubIds = new HubIds(fromHubId, toHubId);

        // Mock된 서비스에서 반환할 데이터 생성
        GetRouteResponseDto getRouteResponseDto1 = new GetRouteResponseDto(
            UUID.randomUUID(),
            "Hub 1",
            "Address 1",
            37.7749,
            -122.4194
        );
        GetRouteResponseDto getRouteResponseDto2 = new GetRouteResponseDto(
            UUID.randomUUID(),
            "Hub 2",
            "Address 2",
            37.7749,
            -122.4194
        );

        // HubService mock
        when(hubService.findByToHubIdAndFromHubId(any(
            takeoff.logistics_service.msa.hub.hub.application.dto.HubIdsDto.class)))
            .thenReturn(List.of(getRouteResponseDto1, getRouteResponseDto2));

        // When & Then
        mockMvc.perform(post("/api/v1/app/hubs/stopover")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hubIds))) // 요청 본문에 hubIds 객체 전달
            .andExpect(status().isOk()) // HTTP 200 OK 응답 확인
            .andDo(document("hub/find-stopover-routes",(
                    ResourceSnippetParameters
                        .builder()
                        .description("허브를 경로를 탐색합니다")
                        .tag("Hub-Internal"))
                    .requestFields(
                    fieldWithPath("fromHubId").description("출발지 허브 ID"),
                    fieldWithPath("toHubId").description("도착지 허브 ID")
                ),
                responseFields(
                    fieldWithPath("[].hubId").description("허브 ID"),
                    fieldWithPath("[].hubName").description("허브 이름"),
                    fieldWithPath("[].address").description("허브 주소"),
                    fieldWithPath("[].latitude").description("허브 위도"),
                    fieldWithPath("[].longitude").description("허브 경도")
                )
            ));

    }

    @Test
    void 모든_허브_조회_성공() throws Exception {
        // Given
        GetAllHubsDto getAllHubsDto1 = new GetAllHubsDto(
            UUID.randomUUID(),
            "Hub 1",
            "Address 1",
            37.7749,
            -122.4194
        );
        GetAllHubsDto getAllHubsDto2 = new GetAllHubsDto(
            UUID.randomUUID(),
            "Hub 2",
            "Address 2",
            37.7749,
            -122.4194
        );

        // HubService mock
        when(hubService.findAllHub())
            .thenReturn(List.of(getAllHubsDto1, getAllHubsDto2));

        // When & Then
        mockMvc.perform(get("/api/v1/app/hubs/allHub"))
            .andExpect(status().isOk()) // HTTP 200 OK 응답 확인
            .andDo(document("hub/find-all-hubs",(
                    ResourceSnippetParameters
                        .builder()
                        .description("허브를 모두 조회합니다")
                        .tag("Hub-Internal"))
                .responseFields(
                    fieldWithPath("[].hubId").description("허브 ID"),
                    fieldWithPath("[].hubName").description("허브 이름"),
                    fieldWithPath("[].address").description("허브 주소"),
                    fieldWithPath("[].latitude").description("허브 위도"),
                    fieldWithPath("[].longitude").description("허브 경도")
                )
            ));
    }



}