package takeoff.logistics_service.msa.product.stock.presentation.external;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.*;
import takeoff.logistics_service.msa.product.stock.presentation.internal.StockInternalController;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class StockExternalControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private StockInternalController stockInternalController; // 테스트 데이터 준비를 위해 사용

	private UUID createRandomUUID(String seed) {
		return UUID.nameUUIDFromBytes(seed.getBytes());
	}

	private void createTestStock(UUID productId, UUID hubId, int quantity) throws Exception {
		StockIdRequest stockIdRequest = new StockIdRequest(productId, hubId);
		PostStockRequest postStockRequest = new PostStockRequest(stockIdRequest, quantity);

		mockMvc.perform(post("/api/v1/app/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postStockRequest)))
			.andExpect(status().isOk());
	}

	@Test
	void 재고_id로_재고를_조회할_수_있다() throws Exception {
		// given
		UUID productId = createRandomUUID("external-product1");
		UUID hubId = createRandomUUID("external-hub1");

		// 테스트용 재고 생성
		createTestStock(productId, hubId, 100);

		StockIdRequest stockIdRequest = new StockIdRequest(productId, hubId);

		// when & then
		mockMvc.perform(get("/api/v1/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(stockIdRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.stockId.productId").value(productId.toString()))
			.andExpect(jsonPath("$.stockId.hubId").value(hubId.toString()))
			.andExpect(jsonPath("$.quantity").exists())
			.andDo(document("stock-external/find",
				requestFields(
					fieldWithPath("productId").description("조회할 상품 ID"),
					fieldWithPath("hubId").description("조회할 허브 ID")
				),
				responseFields(
					fieldWithPath("stockId").description("재고 ID 정보"),
					fieldWithPath("stockId.productId").description("상품 ID"),
					fieldWithPath("stockId.hubId").description("허브 ID"),
					fieldWithPath("quantity").description("재고 수량"),
					fieldWithPath("updatedAt").description("최종 수정 시간")
				)));
	}

	@Test
	void 재고를_검색할_수_있다() throws Exception {
		// given
		UUID productId = createRandomUUID("external-product2");
		UUID hubId = createRandomUUID("external-hub2");

		createTestStock(productId, hubId, 100);
		createTestStock(createRandomUUID("external-product-extra1"), hubId, 50);
		createTestStock(productId, createRandomUUID("external-hub-extra1"), 75);

		// when & then - 기본 검색 (기본값: sortBy=createdAt, isAsc=false, size=10, page=0)
		mockMvc.perform(get("/api/v1/stock/search")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("stock-external/search-default",
				responseFields(
					fieldWithPath("content").description("재고 목록"),
					fieldWithPath("content[].stockId").description("재고 ID 정보"),
					fieldWithPath("content[].stockId.productId").description("상품 ID"),
					fieldWithPath("content[].stockId.hubId").description("허브 ID"),
					fieldWithPath("content[].quantity").description("재고 수량"),
					fieldWithPath("content[].updatedAt").description("최종 수정 시간"),
					fieldWithPath("page").description("현재 페이지 번호"),
					fieldWithPath("size").description("페이지 크기"),
					fieldWithPath("totalElements").description("전체 요소 수"),
					fieldWithPath("totalPages").description("전체 페이지 수")
				)));

		// 상품 ID로 검색
		mockMvc.perform(get("/api/v1/stock/search")
				.param("productId", productId.toString())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("stock-external/search-by-product-id",
				queryParameters(
					parameterWithName("productId").description("검색할 상품 ID")
				)));

		// 허브 ID로 검색
		mockMvc.perform(get("/api/v1/stock/search")
				.param("hubId", hubId.toString())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("stock-external/search-by-hub-id",
				queryParameters(
					parameterWithName("hubId").description("검색할 허브 ID")
				)));

		// 오름차순 정렬 (isAsc=true)
		mockMvc.perform(get("/api/v1/stock/search")
				.param("isAsc", "true")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("stock-external/search-with-asc-sort",
				queryParameters(
					parameterWithName("isAsc").description("오름차순 여부 (true: 오름차순, false: 내림차순, 기본값: false)")
				)));

		// 정렬 기준 필드 변경 (sortBy)
		mockMvc.perform(get("/api/v1/stock/search")
				.param("sortBy", "updatedAt")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("stock-external/search-with-sort-by",
				queryParameters(
					parameterWithName("sortBy").description("정렬 기준 필드 (기본값: createdAt)")
				)));

		// 페이지 번호 변경 (page)
		mockMvc.perform(get("/api/v1/stock/search")
				.param("page", "1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("stock-external/search-with-page",
				queryParameters(
					parameterWithName("page").description("페이지 번호 (0부터 시작, 기본값: 0)")
				)));

		// 페이지 크기 변경 (size) - 유효한 값(10, 30, 50)만 가능
		mockMvc.perform(get("/api/v1/stock/search")
				.param("size", "30")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("stock-external/search-with-size",
				queryParameters(
					parameterWithName("size").description("페이지 크기 (유효한 값: 10, 30, 50, 기본값: 10)")
				)));

		// 잘못된 페이지 크기 (기본값 10으로 동작해야 함)
		mockMvc.perform(get("/api/v1/stock/search")
				.param("size", "20")  // 유효하지 않은 크기
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size").value(10));  // 기본값 10으로 설정되어야 함

		// 모든 파라미터 조합
		mockMvc.perform(get("/api/v1/stock/search")
				.param("productId", productId.toString())
				.param("hubId", hubId.toString())
				.param("sortBy", "updatedAt")
				.param("isAsc", "true")
				.param("page", "0")
				.param("size", "30")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("stock-external/search-with-all-params",
				queryParameters(
					parameterWithName("productId").description("검색할 상품 ID"),
					parameterWithName("hubId").description("검색할 허브 ID"),
					parameterWithName("sortBy").description("정렬 기준 필드 (기본값: createdAt)"),
					parameterWithName("isAsc").description("오름차순 여부 (true: 오름차순, false: 내림차순, 기본값: false)"),
					parameterWithName("page").description("페이지 번호 (0부터 시작, 기본값: 0)"),
					parameterWithName("size").description("페이지 크기 (유효한 값: 10, 30, 50, 기본값: 10)")
				)));
	}

	@Test
	void 재고를_증가시킬_수_있다() throws Exception {
		// given
		UUID productId = createRandomUUID("external-product3");
		UUID hubId = createRandomUUID("external-hub3");

		// 테스트용 재고 생성
		createTestStock(productId, hubId, 100);

		StockIdRequest stockIdRequest = new StockIdRequest(productId, hubId);
		IncreaseStockRequest increaseStockRequest = new IncreaseStockRequest(stockIdRequest, 50);

		// when & then
		mockMvc.perform(patch("/api/v1/stock/increase")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(increaseStockRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.stockId.productId").value(productId.toString()))
			.andExpect(jsonPath("$.stockId.hubId").value(hubId.toString()))
			.andExpect(jsonPath("$.quantity").exists())
			.andDo(document("stock-external/increase",
				requestFields(
					fieldWithPath("stockId").description("재고 ID 정보"),
					fieldWithPath("stockId.productId").description("상품 ID"),
					fieldWithPath("stockId.hubId").description("허브 ID"),
					fieldWithPath("quantity").description("증가시킬 수량")
				),
				responseFields(
					fieldWithPath("stockId").description("재고 ID 정보"),
					fieldWithPath("stockId.productId").description("상품 ID"),
					fieldWithPath("stockId.hubId").description("허브 ID"),
					fieldWithPath("quantity").description("증가 후 재고 수량")
				)));
	}

	@Test
	void 특정_재고를_증가시킬_수_있다() throws Exception {
		// given
		UUID productId = createRandomUUID("external-product3");
		UUID hubId = createRandomUUID("external-hub3");

		// 테스트용 재고 생성
		createTestStock(productId, hubId, 100);

		StockIdRequest stockIdRequest = new StockIdRequest(productId, hubId);
		IncreaseStockRequest increaseStockRequest = new IncreaseStockRequest(stockIdRequest, 50);

		// when & then
		mockMvc.perform(patch("/api/v1/stock/increase")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(increaseStockRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.stockId.productId").value(productId.toString()))
			.andExpect(jsonPath("$.stockId.hubId").value(hubId.toString()))
			.andExpect(jsonPath("$.quantity").exists())
			.andDo(document("stock-external/increase",
				requestFields(
					fieldWithPath("stockId").description("재고 ID 정보"),
					fieldWithPath("stockId.productId").description("상품 ID"),
					fieldWithPath("stockId.hubId").description("허브 ID"),
					fieldWithPath("quantity").description("증가시킬 수량")
				),
				responseFields(
					fieldWithPath("stockId").description("재고 ID 정보"),
					fieldWithPath("stockId.productId").description("상품 ID"),
					fieldWithPath("stockId.hubId").description("허브 ID"),
					fieldWithPath("quantity").description("증가 후 재고 수량")
				)));
	}

	@Test
	void 특정_재고를_감소시킬_수_있다() throws Exception {
		// given
		UUID productId = createRandomUUID("external-product4");
		UUID hubId = createRandomUUID("external-hub4");

		// 테스트용 재고 생성
		createTestStock(productId, hubId, 100);

		StockIdRequest stockIdRequest = new StockIdRequest(productId, hubId);
		DecreaseStockRequest decreaseStockRequest = new DecreaseStockRequest(stockIdRequest, 30);

		// when & then
		mockMvc.perform(patch("/api/v1/stock/decrease")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(decreaseStockRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.stockId.productId").value(productId.toString()))
			.andExpect(jsonPath("$.stockId.hubId").value(hubId.toString()))
			.andExpect(jsonPath("$.quantity").exists())
			.andDo(document("stock-external/decrease",
				requestFields(
					fieldWithPath("stockId").description("재고 ID 정보"),
					fieldWithPath("stockId.productId").description("상품 ID"),
					fieldWithPath("stockId.hubId").description("허브 ID"),
					fieldWithPath("quantity").description("감소시킬 수량")
				),
				responseFields(
					fieldWithPath("stockId").description("재고 ID 정보"),
					fieldWithPath("stockId.productId").description("상품 ID"),
					fieldWithPath("stockId.hubId").description("허브 ID"),
					fieldWithPath("quantity").description("감소 후 재고 수량")
				)));
	}

	@Test
	void 수량이_남아있지_않은_재고를_삭제할_수_있다() throws Exception {
		// given
		UUID productId = createRandomUUID("external-product5");
		UUID hubId = createRandomUUID("external-hub5");

		// 테스트용 재고 생성
		createTestStock(productId, hubId, 0);

		StockIdRequest stockIdRequest = new StockIdRequest(productId, hubId);

		// when & then
		mockMvc.perform(delete("/api/v1/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(stockIdRequest)))
			.andExpect(status().isNoContent())
			.andDo(document("stock-external/delete",
				requestFields(
					fieldWithPath("productId").description("삭제할 상품 ID"),
					fieldWithPath("hubId").description("삭제할 허브 ID")
				)));
	}
}