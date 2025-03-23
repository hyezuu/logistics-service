package takeoff.logistics_service.msa.slack.infrastructure.client.ai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import takeoff.logistics_service.msa.slack.application.dto.request.PostSlackMessageRequestDto;
import takeoff.logistics_service.msa.slack.infrastructure.client.ai.GeminiWebClient.RequestBody.Content;
import takeoff.logistics_service.msa.slack.infrastructure.client.ai.GeminiWebClient.RequestBody.Part;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GeminiWebClient implements WebClientAdapter{

    private static final String GEMINI_CIRCUIT_BREAKER = "AiService";
    private static final String FALL_BACK_RESPONSE = "fallbackResponse";

    private final WebClient webClient;
    @Value("${ai.api.key}")
    private String apiKey;
    @Value("${ai.api.url}")
    private String aiUrl;

    @CircuitBreaker(name = GEMINI_CIRCUIT_BREAKER, fallbackMethod = FALL_BACK_RESPONSE)
    public Mono<String> sendRequestToGemini(PostSlackMessageRequestDto requestDto) {
        // 필요한 파라미터들 추출
        String prompt = generatePrompt(requestDto);
        log.info("Request URL: {}", aiUrl + apiKey);
        String url = aiUrl + apiKey;

        return webClient.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(generateRequestBody(prompt))) // 요청 본문 설정
            .retrieve()
            .bodyToMono(String.class) // 응답 처리
            .doOnError(error -> log.error("Gemini 응답에 에러가 있습니다: {}", error.getMessage(), error))
            .map(response -> {
                String deadline = extractDeadline(response);
                log.info("발송 시한: {}", deadline);

                return generateResultMessage(requestDto, deadline);
            });
    }

    private String generatePrompt(PostSlackMessageRequestDto requestDto) {
        StringBuilder promptBuilder = getStringBuilder(requestDto);
        // 추가적인 인스트럭션
        promptBuilder.append("\n").append(AiPromptEnum.PROMPT_INSTRUCTION.getPromptTemplate());

        return promptBuilder.toString();
    }

    private String generateResultMessage(PostSlackMessageRequestDto requestDto, String deadline) {
        StringBuilder promptBuilder = getStringBuilder(requestDto);
        promptBuilder.append(deadline);

        return promptBuilder.toString();
    }

    private RequestBody generateRequestBody(String prompt) {
        Part part = new Part(prompt);
        Content content = new Content(Collections.singletonList(part));
        return new RequestBody(Collections.singletonList(content));
    }

    public static class RequestBody {
        private final List<Content> contents;

        @JsonCreator
        public RequestBody(@JsonProperty("contents") List<Content> contents) {
            this.contents = contents;
        }

        public List<Content> getContents() {
            return contents;
        }

        public static class Content {
            private final List<Part> parts;

            @JsonCreator
            public Content(@JsonProperty("parts") List<Part> parts) {
                this.parts = parts;
            }

            public List<Part> getParts() {
                return parts;
            }
        }

        public static class Part {
            private final String text;

            @JsonCreator
            public Part(@JsonProperty("text") String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }
        }
    }
    public String extractDeadline(String aiResponse) {
        String pattern = "위 내용을 기반으로 도출된 ";
        log.info(aiResponse);
        int startIdx = aiResponse.indexOf(pattern);
        if (startIdx != -1) {
            int endIdx = aiResponse.indexOf("입니다.", startIdx);
            if (endIdx != -1) {
                return aiResponse.substring(startIdx, endIdx + "입니다.".length());
            }
        }
        return "출발 시간을 계산할 수 없습니다.";  // 응답에서 실패한 경우
    }

    private static StringBuilder getStringBuilder(PostSlackMessageRequestDto requestDto) {
        StringBuilder promptBuilder = new StringBuilder();

        // AiPromptEnum을 사용해 동적으로 프롬프트 생성
        promptBuilder.append(AiPromptEnum.ORDER_NUMBER.getPromptTemplate()).append(requestDto.orderNumber()).append("\n");
        promptBuilder.append(AiPromptEnum.COMPANY_NAME.getPromptTemplate()).append(requestDto.companyName()).append("\n");
        promptBuilder.append(AiPromptEnum.PRODUCT_INFO.getPromptTemplate()).append(requestDto.productInfo()).append("\n");
        promptBuilder.append(AiPromptEnum.ORDER_REQUEST.getPromptTemplate()).append(requestDto.orderRequest()).append("\n");
        promptBuilder.append(AiPromptEnum.FROM_HUB_NAME.getPromptTemplate()).append(requestDto.fromHubName()).append("\n");
        promptBuilder.append(AiPromptEnum.STOP_OVER_HUB_NAMES.getPromptTemplate()).append(String.join(", ", requestDto.stopoverHubNames().hubNames())).append("\n");
        promptBuilder.append(AiPromptEnum.TO_HUB_NAME.getPromptTemplate()).append(requestDto.toHubName()).append("\n");
        promptBuilder.append(AiPromptEnum.DELIVERY_USERS.getPromptTemplate()).append(String.join(", ", requestDto.deliveryUsers().deliveryUserNames())).append("\n");
        return promptBuilder;
    }

    //ai에 대한 요청은 내부적으로 슬랙에 보내기 위함으로 별도의 retry 설정은 하지 않고
    //30분 이내로 출발해 달라는 요청을 남깁니다.
    private Mono<String> fallbackResponse(Throwable t) {
        log.error("Gemini AI 서비스 장애 발생 {}", t.getMessage());
        return Mono.just("AI 서비스가 일시적으로 응답할 수 없습니다. 30분 이내로 출발 해주세요.");
    }

}
