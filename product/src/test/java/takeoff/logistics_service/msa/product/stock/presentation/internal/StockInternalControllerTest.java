package takeoff.logistics_service.msa.product.stock.presentation.internal;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.AbortStockRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.PostStockRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.PrepareStockRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.StockIdRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.StockItemRequest;

@SpringBootTest
@AutoConfigureRestDocs(
	uriPort = 19001
)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class StockInternalControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private UUID createRandomUUID(String seed) {
		return UUID.nameUUIDFromBytes(seed.getBytes());
	}

	private final String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
		+ ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6M"
		+ "TUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";

	@Test
	void 재고를_성공적으로_생성할_수_있다() throws Exception {
		// given
		UUID productId = createRandomUUID("product1");
		UUID hubId = createRandomUUID("hub1");

		StockIdRequest stockIdRequest = new StockIdRequest(productId, hubId);
		PostStockRequest postStockRequest = new PostStockRequest(stockIdRequest, 100);

		// when & then
		mockMvc.perform(post("/api/v1/app/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(postStockRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.stockId.productId").value(productId.toString()))
			.andExpect(jsonPath("$.stockId.hubId").value(hubId.toString()))
			.andExpect(jsonPath("$.quantity").value(100))
			.andDo(document("재고 생성",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Stock-Internal")
					.summary("재고 생성")
					.description("재고를 생성하기 위한 엔드포인트입니다.")
					.requestFields(
						fieldWithPath("stockId").description("재고 ID 정보"),
						fieldWithPath("stockId.productId").description("상품 ID"),
						fieldWithPath("stockId.hubId").description("허브 ID"),
						fieldWithPath("quantity").description("재고 수량")
					).build()
				)));
	}

	@Test
	void 상품_id로_조회_요청시_하나의_재고를_반환한다() throws Exception {
		// given
		UUID productId = createRandomUUID("product2");
		UUID hubId = createRandomUUID("hub2");

		StockIdRequest stockIdRequest = new StockIdRequest(productId, hubId);
		PostStockRequest postStockRequest = new PostStockRequest(stockIdRequest, 100);

		mockMvc.perform(post("/api/v1/app/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(postStockRequest)))
			.andExpect(status().isOk());

		// when & then
		mockMvc.perform(get("/api/v1/app/stock")
				.param("productId", productId.toString()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.stockId.productId").value(productId.toString()))
			.andExpect(jsonPath("$.stockId.hubId").value(hubId.toString()))
			.andDo(document("상품 ID로 재고 조회",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Stock-Internal")
					.summary("상품 ID로 재고 조회")
					.description("상품 ID로 재고를 조회합니다")
					.queryParameters(
						parameterWithName("productId").description("상품 ID")
					)
					.build()
				)));
	}

	@Test
	void 여러개의_재고_수량을_차감할_수_있다() throws Exception {
		// given
		UUID productId1 = createRandomUUID("product3");
		UUID productId2 = createRandomUUID("product4");
		UUID hubId = createRandomUUID("hub3");

		// 첫 번째 재고 생성
		StockIdRequest stockIdRequest1 = new StockIdRequest(productId1, hubId);
		PostStockRequest postStockRequest1 = new PostStockRequest(stockIdRequest1, 100);

		mockMvc.perform(post("/api/v1/app/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(postStockRequest1)))
			.andExpect(status().isOk());

		// 두 번째 재고 생성
		StockIdRequest stockIdRequest2 = new StockIdRequest(productId2, hubId);
		PostStockRequest postStockRequest2 = new PostStockRequest(stockIdRequest2, 100);

		mockMvc.perform(post("/api/v1/app/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(postStockRequest2)))
			.andExpect(status().isOk());

		// 재고 차감 요청 준비
		StockItemRequest stockItem1 = new StockItemRequest(stockIdRequest1, 10);
		StockItemRequest stockItem2 = new StockItemRequest(stockIdRequest2, 15);
		PrepareStockRequest prepareStockRequest = new PrepareStockRequest(
			Arrays.asList(stockItem1, stockItem2));

		// when & then
		mockMvc.perform(post("/api/v1/app/stock/prepare")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(prepareStockRequest)))
			.andExpect(status().isOk())
			.andDo(document("재고 준비를 위한 차감",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Stock-Internal")
					.summary("재고 준비")
					.description("주문 준비를 위해 여러 재고의 수량을 차감합니다")
					.requestFields(
						fieldWithPath("stocks").description("준비할 재고 목록"),
						fieldWithPath("stocks[].stockId").description("재고 ID 정보"),
						fieldWithPath("stocks[].stockId.productId").description("상품 ID"),
						fieldWithPath("stocks[].stockId.hubId").description("허브 ID"),
						fieldWithPath("stocks[].quantity").description("준비할 수량")
					)
					.build()
				)));
	}

	@Test
	void 여러개의_재고_수량을_복구할_수_있다() throws Exception {
		// given
		UUID productId1 = createRandomUUID("product5");
		UUID productId2 = createRandomUUID("product6");
		UUID hubId = createRandomUUID("hub4");

		// 첫 번째 재고 생성
		StockIdRequest stockIdRequest1 = new StockIdRequest(productId1, hubId);
		PostStockRequest postStockRequest1 = new PostStockRequest(stockIdRequest1, 100);

		mockMvc.perform(post("/api/v1/app/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(postStockRequest1)))
			.andExpect(status().isOk());

		// 두 번째 재고 생성
		StockIdRequest stockIdRequest2 = new StockIdRequest(productId2, hubId);
		PostStockRequest postStockRequest2 = new PostStockRequest(stockIdRequest2, 100);

		mockMvc.perform(post("/api/v1/app/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(postStockRequest2)))
			.andExpect(status().isOk());

		// 재고 차감 요청 준비
		StockItemRequest stockItem1 = new StockItemRequest(stockIdRequest1, 10);
		StockItemRequest stockItem2 = new StockItemRequest(stockIdRequest2, 15);
		PrepareStockRequest prepareStockRequest = new PrepareStockRequest(
			Arrays.asList(stockItem1, stockItem2));

		// 먼저 재고 준비
		mockMvc.perform(post("/api/v1/app/stock/prepare")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(prepareStockRequest)))
			.andExpect(status().isOk());

		// 재고 복구 요청 준비
		AbortStockRequest abortStockRequest = new AbortStockRequest(
			Arrays.asList(stockItem1, stockItem2));

		// when & then
		mockMvc.perform(post("/api/v1/app/stock/abort")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(abortStockRequest)))
			.andExpect(status().isOk())
			.andDo(document("재고 취소 및 복구",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Stock-Internal")
					.summary("재고 취소")
					.description("주문 취소를 위해 여러 재고의 수량을 복구합니다")
					.requestFields(
						fieldWithPath("stocks").description("취소할 재고 목록"),
						fieldWithPath("stocks[].stockId").description("재고 ID 정보"),
						fieldWithPath("stocks[].stockId.productId").description("상품 ID"),
						fieldWithPath("stocks[].stockId.hubId").description("허브 ID"),
						fieldWithPath("stocks[].quantity").description("취소할 수량")
					)
					.build()
				)));
	}

	@Test
	void 상품_id로_여러_재고를_삭제할_수_있다() throws Exception {
		// given
		UUID productId = createRandomUUID("product7");
		UUID hubId = createRandomUUID("hub5");

		StockIdRequest stockIdRequest = new StockIdRequest(productId, hubId);
		PostStockRequest emptyStock = new PostStockRequest(stockIdRequest, 0);

		mockMvc.perform(post("/api/v1/app/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(emptyStock)))
			.andExpect(status().isOk());

		// when & then
		mockMvc.perform(delete("/api/v1/app/stock/all-by-product")
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header(HttpHeaders.AUTHORIZATION, token)
				.queryParam("productId", productId.toString()))
			.andExpect(status().isOk())
			.andDo(document("상품 ID로 모든 재고 삭제",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Stock-Internal")
					.summary("상품 ID로 모든 재고 삭제")
					.description("상품 ID에 해당하는 모든 재고를 삭제합니다(재고가 남아있지 않은 경우)")
					.queryParameters(
						parameterWithName("productId").description("삭제할 상품 ID")
					)
					.build()
				)));
	}

	@Test
	void 허브_id로_여러_재고를_삭제할_수_있다() throws Exception {
		// given
		UUID productId = createRandomUUID("product8");
		UUID hubId = createRandomUUID("hub6");

		StockIdRequest stockIdRequest = new StockIdRequest(productId, hubId);
		PostStockRequest postStockRequest = new PostStockRequest(stockIdRequest, 0);

		mockMvc.perform(post("/api/v1/app/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(postStockRequest)))
			.andExpect(status().isOk());

		// when & then
		mockMvc.perform(delete("/api/v1/app/stock/all-by-hub")
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header(HttpHeaders.AUTHORIZATION, token)
				.queryParam("hubId", hubId.toString()))
			.andExpect(status().isOk())
			.andDo(document("허브 ID로 모든 재고 삭제",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Stock-Internal")
					.summary("허브 ID로 모든 재고 삭제")
					.description("허브 ID에 해당하는 모든 재고를 삭제합니다(재고가 남아있지 않은 경우)")
					.queryParameters(
						parameterWithName("hubId").description("삭제할 허브 ID")
					)
					.build()
				)));
	}
}