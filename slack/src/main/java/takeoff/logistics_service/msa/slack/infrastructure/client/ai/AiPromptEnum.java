package takeoff.logistics_service.msa.slack.infrastructure.client.ai;

import lombok.Getter;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
@Getter
public enum AiPromptEnum {

    //요청 구문 생성
    ORDER_NUMBER("주문 번호: "),
    COMPANY_NAME("주문자 정보: "),
    PRODUCT_INFO("상품 정보: "),
    ORDER_REQUEST("요청 사항: "),
    FROM_HUB_NAME("발송지: "),
    STOP_OVER_HUB_NAMES("경유지: "),
    TO_HUB_NAME("도착지: "),
    DELIVERY_USERS("배송 담당자: "),
    PROMPT_INSTRUCTION(
        "요청 사항에 시간에 도착하기 위해 언제 출발해야 하는지 답변해주세요."
            + "만약 요청 사항에 시간에 대한 정보가 적혀있지 않다면 마지막 발송 시한에 현재 시간에 2시간을 더한 값을 넣어주세요."
            + "정확하지 않아도 되고 대략적으로 몇월 며칠 몇시에 출발해야하는가를 답변해주세요."
            + "기본적인 1톤 트럭이 도로 상황은 차가 막히지 않는다로 가정합니다."
            + "평균 시속은 80km/h 이며, 하역 및 적재 시간은 없다고 하고 운전자 휴식 시간도 없습니다."
            + "위 정보를 기반으로 '몇월 며칠 몇시에 출발해야 하는지' 구체적이지 않아도 돼 대략적으로 출발 시간을 포함하여 아래 형식으로 답변을 주세요.\n"
            + "위 내용을 기반으로 도출된 최종 발송 시한은 MM월 DD일 HH시 입니다."

    );

    private final String promptTemplate;
    AiPromptEnum(String promptTemplate) {
        this.promptTemplate = promptTemplate;
    }
}
