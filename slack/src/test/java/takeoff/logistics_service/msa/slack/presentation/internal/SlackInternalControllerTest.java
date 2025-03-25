package takeoff.logistics_service.msa.slack.presentation.internal;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDateTime;
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
import takeoff.logistics_service.msa.slack.application.dto.response.PostContentsResponseDto;
import takeoff.logistics_service.msa.slack.application.dto.response.PostSlackResponseDto;
import takeoff.logistics_service.msa.slack.application.service.SlackAlarmService;
import takeoff.logistics_service.msa.slack.application.service.SlackServiceImpl;
import takeoff.logistics_service.msa.slack.infrastructure.client.ai.GeminiWebClient;
import takeoff.logistics_service.msa.slack.presentation.dto.DeliveryUsers;
import takeoff.logistics_service.msa.slack.presentation.dto.StopoverHubNames;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PostContentsRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PostSlackRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PostUserSlackRequest;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 21.
 */
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class SlackInternalControllerTest {


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
    void 슬랙_메시지_저장_성공_AI() throws Exception {
        // Given
        Long userId = 1L;
        UUID slackId = UUID.randomUUID();

        PostSlackRequest postSlackRequest = new PostSlackRequest(
            12345, // 주문 번호
            "테스트 회사", // 회사명
            "테스트 상품 정보", // 상품 정보
            "긴급 배송 요청", // 주문 요청사항
            "서울 허브", // 출발 허브명
            new StopoverHubNames(List.of("대전 허브", "부산 허브")), // 경유 허브 목록
            "부산 허브", // 도착 허브명
            new DeliveryUsers(List.of("배송 기사1", "배송 기사2")), // 배송 기사 목록
            "테스트 배송 담당자" // 회사 배송 담당자 이름
        );

        UserInfoDto userInfo = new UserInfoDto(1L, UserRole.COMPANY_MANAGER);
        PostSlackResponseDto postSlackResponseDto = new PostSlackResponseDto(
            slackId, userInfo.userId(), new PostContentsResponseDto("긴급 배송 요청", LocalDateTime.now()));

        when(slackServiceImpl.saveSlackMessage(any(), any()))
            .thenReturn(postSlackResponseDto);

        // When & Then
        mockMvc.perform(post("/api/v1/app/slacks/message/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(postSlackRequest)))
            .andExpect(status().isOk())
            .andDo(document("slack/save-message",
                ResourceSnippetParameters.builder()
                    .description("Slack 메시지를 저장합니다")
                    .tag("Slack")
                    .pathParameters(
                        parameterWithName("userId").description("Slack 메시지를 저장할 사용자 ID")
                    )
                    .requestFields(
                        fieldWithPath("orderNumber").description("주문 번호"),
                        fieldWithPath("companyName").description("회사명"),
                        fieldWithPath("productInfo").description("상품 정보"),
                        fieldWithPath("orderRequest").description("주문 요청사항"),
                        fieldWithPath("fromHubName").description("출발 허브명"),
                        fieldWithPath("stopoverHubNames.hubNames").description("경유 허브 목록"),
                        fieldWithPath("toHubName").description("도착 허브명"),
                        fieldWithPath("deliveryUsers.deliveryUserNames").description("배송 기사 목록"),
                        fieldWithPath("companyDeliveryUserName").description("회사 배송 담당자 이름")
                    )
                    .responseFields(
                        fieldWithPath("slackId").description("저장된 Slack ID"),
                        fieldWithPath("userId").description("Slack을 등록한 사용자 ID"),
                        fieldWithPath("postContentsResponse.message").description("저장된 메시지 내용"),
                        fieldWithPath("postContentsResponse.sentAt").description("메시지 전송 시간")
                    )
            ));
    }

    @Test
    void 슬랙_메시지_저장_성공_USER() throws Exception {
        // Given
        Long userId = 1L;
        UUID slackId = UUID.randomUUID();
        String message = "긴급 배송 요청";

        PostContentsRequest postContentsRequest = new PostContentsRequest(message);
        PostUserSlackRequest postUserSlackRequest = new PostUserSlackRequest(postContentsRequest);

        when(slackServiceImpl.saveSlackMessageToUser(any(), any()))
            .thenReturn(new PostSlackResponseDto(slackId, userId,
                new PostContentsResponseDto(message, LocalDateTime.now())));

        // When & Then
        mockMvc.perform(post("/api/v1/app/slacks/message/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(postUserSlackRequest)))
            .andExpect(status().isOk())
            .andDo(document("slack/save-message-to-user",
                ResourceSnippetParameters.builder()
                    .description("사용자의 Slack 메시지를 저장합니다")
                    .tag("Slack")
                    .pathParameters(
                        parameterWithName("userId").description("Slack 메시지를 저장할 사용자 ID")
                    )
                    .requestFields(
                        fieldWithPath("postContentsRequest.message").description("슬랙 메시지 내용")
                    )
                    .responseFields(
                        fieldWithPath("slackId").description("저장된 Slack ID"),
                        fieldWithPath("userId").description("Slack을 등록한 사용자 ID"),
                        fieldWithPath("postContentsResponse.message").description("저장된 메시지 내용"),
                        fieldWithPath("postContentsResponse.sentAt").description("메시지 전송 시간")
                    )
            ));
    }
}