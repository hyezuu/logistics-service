package takeoff.logistics_service.msa.slack.presentation.external;


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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.slack.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.slack.application.dto.response.GetContentsResponseDto;
import takeoff.logistics_service.msa.slack.application.dto.response.GetSlackResponseDto;
import takeoff.logistics_service.msa.slack.application.dto.response.PatchContentsResponseDto;
import takeoff.logistics_service.msa.slack.application.dto.response.PatchSlackResponseDto;
import takeoff.logistics_service.msa.slack.application.dto.response.PostContentsResponseDto;
import takeoff.logistics_service.msa.slack.application.dto.response.PostSlackResponseDto;
import takeoff.logistics_service.msa.slack.application.dto.response.SearchContentsResponseDto;
import takeoff.logistics_service.msa.slack.application.dto.response.SearchSlackResponseDto;
import takeoff.logistics_service.msa.slack.application.service.SlackAlarmService;
import takeoff.logistics_service.msa.slack.application.service.SlackConstant;
import takeoff.logistics_service.msa.slack.application.service.SlackServiceImpl;
import takeoff.logistics_service.msa.slack.infrastructure.client.ai.GeminiWebClient;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PatchContentsRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PatchSlackRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PostContentsRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PostUserSlackRequest;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 20.
 */
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class SlackExternalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SlackAlarmService slackAlarmService;

    @MockitoBean
    private SlackServiceImpl slackServiceImpl;

    @MockitoBean
    private GeminiWebClient geminiWebClient;

    private UUID createRandomUUID(String seed) {
        return UUID.nameUUIDFromBytes(seed.getBytes());
    }


    @Test
    void 존재하는_ID_조회_성공() throws Exception {
        // Given
        UUID slackId = createRandomUUID("test-slack-id");
        PostContentsRequest postContentsRequest = new PostContentsRequest("test");
        PostUserSlackRequest userSlackRequest = new PostUserSlackRequest(postContentsRequest);
        Long userId = 1L;
        // SlackResponseDto 객체 준비
        PostSlackResponseDto mockResponse = new PostSlackResponseDto(slackId, userId, new PostContentsResponseDto("test", LocalDateTime.now()));
        GetSlackResponseDto getSlackResponseDto = new GetSlackResponseDto(slackId, userId, new GetContentsResponseDto("test", LocalDateTime.now()));

        when(slackServiceImpl.saveSlackMessageToUser(any(), any())).thenReturn((mockResponse));
        when(slackServiceImpl.findBySlackId(any())).thenReturn(getSlackResponseDto);

        // Slack 메시지 전송 Mock 설정
        doNothing().when(slackAlarmService)
            .sendSlackMessageToChannel(any(), eq(SlackConstant.USER_CHANNEL));
        doNothing().when(slackAlarmService)
            .sendSlackMessageToChannel(any(), eq(SlackConstant.PROJECT_CHANNEL));

        mockMvc.perform(post("/api/v1/app/slacks/message/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userSlackRequest)))
            .andExpect(status().isOk());

        // When & Then
        mockMvc.perform(get("/api/v1/slacks/{slackId}", slackId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.slackId").value(slackId.toString()))
            .andExpect(jsonPath("$.userId").value(userId))
            .andExpect(jsonPath("$.getContentsResponse.message").value("test"))
            .andExpect(jsonPath("$.getContentsResponse.sent_At").exists())
            .andDo(document("slack/find-by-slack-id", (
                    ResourceSnippetParameters
                        .builder()
                        .description("Slack ID로 메시지를 조회합니다")
                        .tag("Slack-External"))
                    .pathParameters(
                        parameterWithName("slackId").description("조회할 Slack ID (UUID)")
                    ),
                responseFields(
                    fieldWithPath("slackId").description("Slack의 ID"),
                    fieldWithPath("userId").description("Slack을 등록한 사용자 ID"),
                    fieldWithPath("getContentsResponse").description("Slack 메시지 내용"),
                    fieldWithPath("getContentsResponse.message").description("Slack 메시지 내용"),
                    fieldWithPath("getContentsResponse.sent_At").description("메시지 전송 시간")
                )
            ));
    }
    @Test
    void 존재하는_ID_수정_성공() throws Exception {
        // Given
        UUID slackId = createRandomUUID("test-slack-id");
        PostContentsRequest postContentsRequest = new PostContentsRequest("test");
        PostUserSlackRequest userSlackRequest = new PostUserSlackRequest(postContentsRequest);
        Long userId = 1L;
        // SlackResponseDto 객체 준비
        PostSlackResponseDto mockResponse = new PostSlackResponseDto(slackId, userId, new PostContentsResponseDto("updated-message", LocalDateTime.now()));
        when(slackServiceImpl.saveSlackMessageToUser(any(), any())).thenReturn((mockResponse));
        when(slackServiceImpl.updateBySlack(any(),any()))
            .thenReturn(new PatchSlackResponseDto
                (slackId, userId, new PatchContentsResponseDto("updated-message", LocalDateTime.now())));
        // Slack 메시지 전송 Mock 설정
        doNothing().when(slackAlarmService)
            .sendSlackMessageToChannel(any(), eq(SlackConstant.USER_CHANNEL));
        doNothing().when(slackAlarmService)
            .sendSlackMessageToChannel(any(), eq(SlackConstant.PROJECT_CHANNEL));
        mockMvc.perform(post("/api/v1/app/slacks/message/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userSlackRequest)))
            .andExpect(status().isOk());
        // 메시지 수정 요청
        PatchContentsRequest patchContentsRequest = new PatchContentsRequest("update");
        PatchSlackRequest patchSlackRequest = new PatchSlackRequest(userId, patchContentsRequest);
        mockMvc.perform(patch("/api/v1/slacks/{slackId}", slackId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(patchSlackRequest)))
            .andExpect(status().isOk())
            .andDo(document("slack/update-by-slack", (
                    ResourceSnippetParameters
                        .builder()
                        .description("Slack 메시지를 수정합니다")
                        .tag("Slack-External"))
                    .pathParameters(
                        parameterWithName("slackId").description("수정할 Slack의 UUID")
                    ),
                requestFields(
                    fieldWithPath("userId").description("Slack을 수정하는 사용자 ID"),
                    fieldWithPath("patchContentsRequest.message").description("수정할 Slack 메시지 내용")
                ),
                responseFields(
                    fieldWithPath("slackId").description("Slack의 고유 ID"),
                    fieldWithPath("userId").description("Slack을 수정한 사용자 ID"),
                    fieldWithPath("patchContentsResponse").description("수정된 Slack 메시지 정보"),
                    fieldWithPath("patchContentsResponse.message").description("수정된 Slack 메시지 내용"),
                    fieldWithPath("patchContentsResponse.sentAt").description("수정된 메시지 전송 시간")
                )
            ));
    }
    @Test
    void 존재하는_ID_삭제_성공() throws Exception {
        // Given
        UUID slackId = createRandomUUID("test-slack-id");
        PostContentsRequest postContentsRequest = new PostContentsRequest("test");
        PostUserSlackRequest userSlackRequest = new PostUserSlackRequest(postContentsRequest);
        Long userId = 1L;
        // SlackResponseDto 객체 준비
        PostSlackResponseDto mockResponse = new PostSlackResponseDto(slackId, userId, new PostContentsResponseDto("updated-message", LocalDateTime.now()));
        when(slackServiceImpl.saveSlackMessageToUser(any(), any())).thenReturn((mockResponse));
        mockMvc.perform(post("/api/v1/app/slacks/message/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userSlackRequest)))
            .andExpect(status().isOk());
        mockMvc.perform(delete("/api/v1/slacks/{slackId}/{userId}", slackId, userId))
            .andExpect(status().isNoContent())
            .andDo(document("slack/delete-by-slack", (
                ResourceSnippetParameters
                    .builder()
                    .description("Slack 메시지를 삭제합니다")
                    .tag("Slack-External"))
                .pathParameters(
                    parameterWithName("slackId").description("삭제할 Slack의 UUID"),
                    parameterWithName("userId").description("Slack을 삭제하는 사용자 ID")
                )
            ));
    }
    @Test
    void Slack_검색_성공() throws Exception {
        // Given
        String message = "test";
        boolean isAsc = true;
        String sortBy = "sentAt";
        int page = 0;
        int size = 10;
        UUID slackId = createRandomUUID("test-slack-id");
        PostContentsRequest postContentsRequest = new PostContentsRequest("test");
        PostUserSlackRequest userSlackRequest = new PostUserSlackRequest(postContentsRequest);
        Long userId = 1L;
        // SlackResponseDto 객체 준비
        PostSlackResponseDto mockResponse = new PostSlackResponseDto(slackId, userId, new PostContentsResponseDto("updated-message", LocalDateTime.now()));
        when(slackServiceImpl.saveSlackMessageToUser(any(), any())).thenReturn(mockResponse);
        when(slackServiceImpl.searchSlack(any()))
            .thenReturn(new PaginatedResultDto<>(
                List.of(new SearchSlackResponseDto(slackId, userId,
                    new SearchContentsResponseDto("test", LocalDateTime.now())
                )),
                0,
                10,
                1L,
                1
            ));
        // When & Then
        mockMvc.perform(get("/api/v1/slacks/search")
                .param("message", message)
                .param("isAsc", String.valueOf(isAsc))
                .param("sortBy", sortBy)
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("slack/search", (
                    ResourceSnippetParameters
                        .builder()
                        .description("Slack 메시지를 검색합니다")
                        .tag("Slack-External"))
                    .queryParameters(
                        parameterWithName("message").description("검색할 메시지"),
                        parameterWithName("isAsc").description("정렬 순서 (true: 오름차순, false: 내림차순)"),
                        parameterWithName("sortBy").description("정렬 기준 필드 (예: sentAt)"),
                        parameterWithName("page").description("조회할 페이지 번호"),
                        parameterWithName("size").description("페이지 크기")
                    ),
                responseFields(
                    fieldWithPath("content[].slackId").description("Slack 메시지의 UUID"),
                    fieldWithPath("content[].userId").description("Slack 메시지를 보낸 사용자 ID"),
                    fieldWithPath("content[].searchContentsResponse.message").description("Slack 메시지 내용"),
                    fieldWithPath("content[].searchContentsResponse.sentAt").description("Slack 메시지 전송 시간"),
                    fieldWithPath("page").description("현재 페이지 번호"),
                    fieldWithPath("size").description("페이지 크기"),
                    fieldWithPath("totalElements").description("총 검색된 요소 수"),
                    fieldWithPath("totalPages").description("총 페이지 수")
                )
            ));
    }
}