package takeoff.logistics_service.msa.hub.hub.presentation.external;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.hub.hub.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PaginatedResultDtoList;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PaginatedResultHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PatchHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PostHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.SearchHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.service.HubServiceImpl;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.LocationApi;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PatchHubRequest;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PostHubRequest;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.SearchHubRequest;
import takeoff.logistics_service.msa.hub.hubroute.application.service.HubRouteServiceImpl;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.client.external.NaverDirectionWebClient;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 21.
 */
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class HubExternalControllerTest {

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
    void 허브_저장_성공() throws Exception {
        // Given
        String hubName = "서울 허브";
        String address = "서울시 강남구";
        Double latitude = 37.4979;
        Double longitude = 127.0276;

        LocationApi locationApi = new LocationApi(address, latitude, longitude);
        PostHubRequest postHubRequest = new PostHubRequest(hubName, locationApi);

        UUID hubId = UUID.randomUUID();

        // HubService mock
        when(hubService.saveHub(any())).thenReturn(
            new PostHubResponseDto(hubId, hubName, address, latitude, longitude)
        );

        // When & Then
        mockMvc.perform(post("/api/v1/hubs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(postHubRequest)))
            .andExpect(status().isOk())
            .andDo(document("hub/save-hub", (
                        ResourceSnippetParameters
                            .builder()
                            .description("허브를 생성합니다")
                            .tag("Hub-External"))
                    .requestFields(
                    fieldWithPath("hubName").description("허브 이름"),
                    fieldWithPath("locationApi.address").description("허브 주소"),
                    fieldWithPath("locationApi.latitude").description("허브 위도"),
                    fieldWithPath("locationApi.longitude").description("허브 경도")
                ),
                responseFields(
                    fieldWithPath("hubId").description("저장된 허브 ID"),
                    fieldWithPath("hubName").description("허브 이름"),
                    fieldWithPath("address").description("허브 주소"),
                    fieldWithPath("latitude").description("허브 위도"),
                    fieldWithPath("longitude").description("허브 경도")
                )
            ));
    }


    @Test
    void 허브_수정_성공() throws Exception {
        // Given
        UUID hubId = UUID.randomUUID();
        String updatedHubName = "서울 허브(업데이트)";
        String updatedAddress = "서울시 서초구";
        Double updatedLatitude = 37.4975;
        Double updatedLongitude = 127.0277;

        LocationApi updatedLocationApi = new LocationApi(updatedAddress, updatedLatitude,
            updatedLongitude);
        PatchHubRequest patchHubRequest = new PatchHubRequest(updatedHubName);

        // HubService mock
        when(hubService.updateHub(eq(hubId), any())).thenReturn(
            new PatchHubResponseDto(hubId, updatedHubName, updatedAddress, updatedLatitude,
                updatedLongitude)
        );

        // When & Then
        mockMvc.perform(patch("/api/v1/hubs/{hubId}", hubId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(patchHubRequest)))
            .andExpect(status().isOk())
            .andDo(document("hub/update-hub",(
                    ResourceSnippetParameters
                        .builder()
                        .description("허브를 수정합니다")
                        .tag("Hub-External"))
                    .pathParameters(
                    parameterWithName("hubId").description("허브 ID")
                ),
                requestFields(
                    fieldWithPath("hubName").description("수정할 허브 이름")
                ),
                responseFields(
                    fieldWithPath("hubId").description("수정된 허브 ID"),
                    fieldWithPath("hubName").description("수정된 허브 이름"),
                    fieldWithPath("address").description("수정된 허브 주소"),
                    fieldWithPath("latitude").description("수정된 허브 위도"),
                    fieldWithPath("longitude").description("수정된 허브 경도")
                )
            ));
    }


    @Test
    void 허브_삭제_성공() throws Exception {
        // Given
        UUID hubId = UUID.randomUUID();
        UserInfoDto userInfoDto = new UserInfoDto(1L, UserRole.MASTER_ADMIN);

        // HubService mock
        doNothing().when(hubService).deleteHub(hubId,userInfoDto.userId());

        // When & Then
        mockMvc.perform(delete("/api/v1/hubs/{hubId}", hubId))
            .andExpect(status().isNoContent()) // HTTP 204 No Content 응답 확인
            .andDo(document("hub/delete-hub",(
                    ResourceSnippetParameters
                        .builder()
                        .description("허브를 삭제합니다")
                        .tag("Hub-External"))
                .pathParameters(
                    parameterWithName("hubId").description("삭제할 허브 ID")
                )
            ));

    }

    @Test
    void 허브_검색_성공() throws Exception {
        // Given
        SearchHubRequest request = new SearchHubRequest(
            "Test Hub",
            "Test Address",
            true,
            "hubName",
            0,
            10
        );

        // Mock된 서비스에서 반환할 데이터 생성
        PaginatedResultDto<SearchHubResponseDto> paginatedResult = new PaginatedResultDto<>(
            List.of(
                new SearchHubResponseDto(UUID.randomUUID(), "Test Hub", "Test Address", 37.7749, -122.4194)
            ),
            0,
            10,
            1L,
            1
        );
        PaginatedResultHubResponseDto pageResult = new PaginatedResultHubResponseDto(
            PaginatedResultDtoList.from(paginatedResult));

        // HubService mock
        when(hubService.searchHub(any())).thenReturn(pageResult);

        // When & Then
        mockMvc.perform(get("/api/v1/hubs")
                .param("hubName", request.hubName())
                .param("address", request.address())
                .param("isAsc", String.valueOf(request.isAsc()))
                .param("sortBy", request.sortBy())
                .param("page", String.valueOf(request.page()))
                .param("size", String.valueOf(request.size())))
            .andExpect(status().isOk()) // HTTP 200 OK 응답 확인
            .andDo(document("hub/search-hub",(
                    ResourceSnippetParameters
                        .builder()
                        .description("허브를 검색합니다")
                        .tag("Hub-External"))
                    .queryParameters(
                    parameterWithName("hubName").description("검색할 허브 이름"),
                    parameterWithName("address").description("검색할 주소"),
                    parameterWithName("isAsc").description("정렬 여부"),
                    parameterWithName("sortBy").description("정렬 기준"),
                    parameterWithName("page").description("페이지 번호"),
                    parameterWithName("size").description("페이지 크기")
                ),
                responseFields(
                    fieldWithPath("content[]").description("검색 결과 리스트"),
                    fieldWithPath("content[0].hubId").description("허브 ID"),
                    fieldWithPath("content[0].hubName").description("허브 이름"),
                    fieldWithPath("content[0].address").description("허브 주소"),
                    fieldWithPath("content[0].latitude").description("허브 위도"),
                    fieldWithPath("content[0].longitude").description("허브 경도"),
                    fieldWithPath("page").description("현재 페이지"),
                    fieldWithPath("size").description("페이지 크기"),
                    fieldWithPath("totalElements").description("전체 요소 개수"),
                    fieldWithPath("totalPages").description("전체 페이지 수")
                )
            ));

    }



}