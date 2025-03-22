package takeoff.logistics_service.msa.product.product.presentation.external;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
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
import takeoff.logistics_service.msa.product.product.application.dto.response.PostStockResponseDto;
import takeoff.logistics_service.msa.product.product.application.service.CompanyClient;
import takeoff.logistics_service.msa.product.product.application.service.HubClient;
import takeoff.logistics_service.msa.product.product.application.service.StockClient;
import takeoff.logistics_service.msa.product.product.presentation.dto.request.PatchProductRequest;
import takeoff.logistics_service.msa.product.product.presentation.dto.request.PostProductRequest;

@SpringBootTest
@AutoConfigureRestDocs(
	uriPort = 19001
)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ProductExternalControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private StockClient stockClient;

	@MockitoBean
	private HubClient hubClient;

	@MockitoBean
	private CompanyClient companyClient;

	private UUID createRandomUUID(String seed) {
		return UUID.nameUUIDFromBytes(seed.getBytes());
	}

	private final LocalDateTime fixedTime
		= LocalDateTime.of(2025, 3, 21, 12, 0, 0);

	@Test
	void 상품을_생성할_수_있다() throws Exception {
		// given
		UUID companyId = createRandomUUID("company1");
		UUID hubId = createRandomUUID("hub1");

		PostProductRequest request = new PostProductRequest(
			"유기농 바나나",
			companyId,
			hubId,
			100
		);

		PostStockResponseDto response = new PostStockResponseDto(
			createRandomUUID("productId1"), hubId, 100, fixedTime);

		doNothing().when(hubClient).findByHubId(any(UUID.class));
		doNothing().when(companyClient).findByCompanyId(any(UUID.class));
		when(stockClient.saveStock(any())).thenReturn(response);

		// when & then
		mockMvc.perform(post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9")
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andDo(document("product-external/create", (
					ResourceSnippetParameters
						.builder()
						.description("상품을 생성합니다")
						.tag("Product-External"))
					.requestFields(
						fieldWithPath("name").description("상품 이름"),
						fieldWithPath("companyId").description("업체 ID"),
						fieldWithPath("hubId").description("허브 ID"),
						fieldWithPath("quantity").description("초기 재고 수량 (null 가능, 기본값 0)")
					),
				requestHeaders(
					headerWithName("X-User-Id").description("User ID"),
					headerWithName("X-User-Role").description("User ID"),
					headerWithName("Authorization").description("JWT 토큰")),
				responseFields(
					fieldWithPath("productId").description("생성된 상품 ID"),
					fieldWithPath("name").description("상품 이름"),
					fieldWithPath("companyId").description("업체 ID"),
					fieldWithPath("hubId").description("허브 ID"),
					fieldWithPath("quantity").description("실제 재고 수량"),
					fieldWithPath("createdAt").description("생성 시간")
				)));
	}

	@Test
	void 초기_재고_수량을_지정하지_않고_상품을_생성할_수_있다() throws Exception {
		// given
		UUID companyId = createRandomUUID("company2");
		UUID hubId = createRandomUUID("hub2");

		PostProductRequest request = new PostProductRequest(
			"프리미엄 사과",
			companyId,
			hubId,
			null  // 수량 미지정
		);

		// FeignClient mock 설정 - void 메서드는 doNothing() 사용
		doNothing().when(hubClient).findByHubId(any(UUID.class));
		doNothing().when(companyClient).findByCompanyId(any(UUID.class));
		when(stockClient.saveStock(any())).thenReturn(
			new PostStockResponseDto(
				createRandomUUID("productId2"),
				hubId,
				0,
				fixedTime
			)
		);

		// when & then
		mockMvc.perform(post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9")
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andDo(document("product-external/create-without-quantity", (
					ResourceSnippetParameters
						.builder()
						.description("초기 재고 수량을 지정하지 않고 상품을 생성합니다. 기본값은 0입니다.")
						.tag("Product-External"))
					.requestFields(
						fieldWithPath("name").description("상품 이름"),
						fieldWithPath("companyId").description("업체 ID"),
						fieldWithPath("hubId").description("허브 ID"),
						fieldWithPath("quantity").description("초기 재고 수량 (null 가능, 기본값 0)")
					),
				requestHeaders(
					headerWithName("X-User-Id").description("User ID"),
					headerWithName("X-User-Role").description("User ID"),
					headerWithName("Authorization").description("JWT 토큰")),
				responseFields(
					fieldWithPath("productId").description("생성된 상품 ID"),
					fieldWithPath("name").description("상품 이름"),
					fieldWithPath("companyId").description("업체 ID"),
					fieldWithPath("hubId").description("허브 ID"),
					fieldWithPath("quantity").description("실제 재고 수량"),
					fieldWithPath("createdAt").description("생성 시간")
				)));
	}

	@Test
	void 상품_이름을_수정할_수_있다() throws Exception {
		// given
		// 1. 상품 생성
		UUID companyId = createRandomUUID("company3");
		UUID hubId = createRandomUUID("hub3");

		PostProductRequest createRequest = new PostProductRequest(
			"유기농 바나나",
			companyId,
			hubId,
			100
		);

		// FeignClient mock 설정 - void 메서드는 doNothing() 사용
		doNothing().when(hubClient).findByHubId(any(UUID.class));
		doNothing().when(companyClient).findByCompanyId(any(UUID.class));
		when(stockClient.saveStock(any())).thenReturn(
			new PostStockResponseDto(
				createRandomUUID("productId3"),
				hubId,
				100,
				fixedTime
			)
		);

		String createResponse = mockMvc.perform(post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
					.header("X-User-Id", "1")
					.header("X-User-Role", "MASTER_ADMIN")
					.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9")
				.content(objectMapper.writeValueAsString(createRequest)))
			.andExpect(status().isCreated())
			.andReturn()
			.getResponse()
			.getContentAsString();

		UUID productId = UUID.fromString(
			objectMapper.readTree(createResponse).get("productId").asText());

		// 2. 상품 이름 수정
		PatchProductRequest updateRequest = new PatchProductRequest("프리미엄 유기농 바나나");

		// when & then
		mockMvc.perform(patch("/api/v1/products/{productId}", productId)
				.contentType(MediaType.APPLICATION_JSON)
					.header("X-User-Id", "1")
					.header("X-User-Role", "MASTER_ADMIN")
					.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9")
				.content(objectMapper.writeValueAsString(updateRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").value(productId.toString()))
			.andExpect(jsonPath("$.name").value("프리미엄 유기농 바나나"))
			.andDo(document("product-external/update-name", (
					ResourceSnippetParameters
						.builder()
						.description("상품 이름을 수정합니다")
						.tag("Product-External"))
					.pathParameters(
						parameterWithName("productId").description("수정할 상품 ID")
					),
				requestFields(
					fieldWithPath("name").description("변경할 상품 이름")
				),
				requestHeaders(
					headerWithName("X-User-Id").description("User ID"),
					headerWithName("X-User-Role").description("User ID"),
					headerWithName("Authorization").description("JWT 토큰")),
				responseFields(
					fieldWithPath("productId").description("상품 ID"),
					fieldWithPath("name").description("변경된 상품 이름"),
					fieldWithPath("companyId").description("업체 ID"),
					fieldWithPath("updatedAt").description("수정 시간")
				)));
	}

	@Test
	void 상품_정보를_조회할_수_있다() throws Exception {
		// given
		// 1. 상품 생성
		UUID companyId = createRandomUUID("company4");
		UUID hubId = createRandomUUID("hub4");

		PostProductRequest createRequest = new PostProductRequest(
			"유기농 바나나",
			companyId,
			hubId,
			100
		);

		// FeignClient mock 설정 - void 메서드는 doNothing() 사용
		doNothing().when(hubClient).findByHubId(any(UUID.class));
		doNothing().when(companyClient).findByCompanyId(any(UUID.class));
		when(stockClient.saveStock(any())).thenReturn(
			new PostStockResponseDto(
				createRandomUUID("productId4"),
				hubId,
				100,
				fixedTime
			)
		);

		String createResponse = mockMvc.perform(post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
					.header("X-User-Id", "1")
					.header("X-User-Role", "MASTER_ADMIN")
					.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9")
				.content(objectMapper.writeValueAsString(createRequest)))
			.andExpect(status().isCreated())
			.andReturn()
			.getResponse()
			.getContentAsString();

		UUID productId = UUID.fromString(
			objectMapper.readTree(createResponse).get("productId").asText());

		// when & then
		mockMvc.perform(get("/api/v1/products/{productId}", productId)
					.header("X-User-Id", "1")
					.header("X-User-Role", "MASTER_ADMIN")
					.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").value(productId.toString()))
			.andExpect(jsonPath("$.name").value("유기농 바나나"))
			.andDo(document("product-external/find", (
					ResourceSnippetParameters
						.builder()
						.description("상품 정보를 조회합니다")
						.tag("Product-External"))
					.pathParameters(
						parameterWithName("productId").description("조회할 상품 ID")
					),
				requestHeaders(
					headerWithName("X-User-Id").description("User ID"),
					headerWithName("X-User-Role").description("User ID"),
					headerWithName("Authorization").description("JWT 토큰")),
				responseFields(
					fieldWithPath("productId").description("상품 ID"),
					fieldWithPath("name").description("상품 이름"),
					fieldWithPath("companyId").description("업체 ID"),
					fieldWithPath("createdAt").description("상품 등록 시간"),
					fieldWithPath("updatedAt").description("최종 수정 시간")
				)));
	}

	@Test
	void 상품을_삭제할_수_있다() throws Exception {
		// given
		// 1. 상품 생성
		UUID companyId = createRandomUUID("company5");
		UUID hubId = createRandomUUID("hub5");

		PostProductRequest createRequest = new PostProductRequest(
			"유기농 바나나",
			companyId,
			hubId,
			0  // 재고가 없는 상품
		);

		// FeignClient mock 설정 - void 메서드는 doNothing() 사용
		doNothing().when(hubClient).findByHubId(any(UUID.class));
		doNothing().when(companyClient).findByCompanyId(any(UUID.class));
		when(stockClient.saveStock(any())).thenReturn(
			new PostStockResponseDto(
				createRandomUUID("productId5"),
				hubId,
				0,
				fixedTime
			)
		);

		String createResponse = mockMvc.perform(post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
					.header("X-User-Id", "1")
					.header("X-User-Role", "MASTER_ADMIN")
					.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9")
				.content(objectMapper.writeValueAsString(createRequest)))
			.andExpect(status().isCreated())
			.andReturn()
			.getResponse()
			.getContentAsString();

		UUID productId = UUID.fromString(
			objectMapper.readTree(createResponse).get("productId").asText());

		// when & then
		mockMvc.perform(delete("/api/v1/products/{productId}", productId)
					.header("X-User-Id", "1")
					.header("X-User-Role", "MASTER_ADMIN")
					.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent())
			.andDo(document("product-external/delete", (
				ResourceSnippetParameters
					.builder()
					.description("상품을 삭제합니다")
					.tag("Product-External"))
				.pathParameters(
					parameterWithName("productId").description("삭제할 상품 ID")
				),
				requestHeaders(
					headerWithName("X-User-Id").description("User ID"),
					headerWithName("X-User-Role").description("User ID"),
					headerWithName("Authorization").description("JWT 토큰"))
			));
	}

	@Test
	void 상품을_검색할_수_있다() throws Exception {
		// given
		// 여러 상품 생성
		UUID companyId = createRandomUUID("company6");
		UUID hubId = createRandomUUID("hub6");

		// FeignClient mock 설정 - void 메서드는 doNothing() 사용
		doNothing().when(hubClient).findByHubId(any(UUID.class));
		doNothing().when(companyClient).findByCompanyId(any(UUID.class));
		when(stockClient.saveStock(any())).thenReturn(
			new PostStockResponseDto(
				createRandomUUID("productId6"),
				hubId,
				100,
				fixedTime
			)
		);

		// 첫 번째 상품 생성
		PostProductRequest request1 = new PostProductRequest("유기농 바나나", companyId, hubId, 100);
		mockMvc.perform(post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
					.header("X-User-Id", "1")
					.header("X-User-Role", "MASTER_ADMIN")
					.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9")
				.content(objectMapper.writeValueAsString(request1)))
			.andExpect(status().isCreated());

		// 두 번째 상품 생성
		PostProductRequest request2 = new PostProductRequest("프리미엄 사과", companyId, hubId, 50);
		mockMvc.perform(post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
					.header("X-User-Id", "1")
					.header("X-User-Role", "MASTER_ADMIN")
					.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9")
				.content(objectMapper.writeValueAsString(request2)))
			.andExpect(status().isCreated());

		// when & then - 기본 검색
		mockMvc.perform(get("/api/v1/products/search")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("product-external/search-default", (
				ResourceSnippetParameters
					.builder()
					.description("상품을 검색합니다. 기본 설정은 최신순, 10개씩 페이징입니다.")
					.tag("Product-External"))
				.responseFields(
					fieldWithPath("content").description("상품 목록"),
					fieldWithPath("content[].productId").description("상품 ID"),
					fieldWithPath("content[].name").description("상품 이름"),
					fieldWithPath("content[].companyId").description("업체 ID"),
					fieldWithPath("content[].createdAt").description("상품 등록 시간"),
					fieldWithPath("content[].updatedAt").description("최종 수정 시간"),
					fieldWithPath("page").description("현재 페이지 번호"),
					fieldWithPath("size").description("페이지 크기"),
					fieldWithPath("totalElements").description("전체 요소 수"),
					fieldWithPath("totalPages").description("전체 페이지 수")
				)));

		// 이름으로 검색
		mockMvc.perform(get("/api/v1/products/search")
				.param("name", "바나나")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("product-external/search-by-name", (
				ResourceSnippetParameters
					.builder()
					.description("상품 이름으로 검색합니다. 부분 일치 검색이 가능합니다.")
					.tag("Product-External"))
				.queryParameters(
					parameterWithName("name").description("검색할 상품 이름")
				)));

		// 업체 ID로 검색
		mockMvc.perform(get("/api/v1/products/search")
				.param("companyId", companyId.toString())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("product-external/search-by-company-id", (
				ResourceSnippetParameters
					.builder()
					.description("업체 ID로 검색합니다. 해당 업체의 모든 상품을 검색합니다.")
					.tag("Product-External"))
				.queryParameters(
					parameterWithName("companyId").description("검색할 업체 ID")
				)));

		// 정렬 및 페이징 검색
		mockMvc.perform(get("/api/v1/products/search")
				.param("sortBy", "name")
				.param("isAsc", "true")
				.param("page", "0")
				.param("size", "10")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("product-external/search-with-sort-and-paging", (
				ResourceSnippetParameters
					.builder()
					.description("정렬 및 페이징 옵션을 적용하여 검색합니다.")
					.tag("Product-External"))
				.queryParameters(
					parameterWithName("sortBy").description("정렬 기준 필드 (기본값: createdAt)"),
					parameterWithName("isAsc").description(
						"오름차순 여부 (true: 오름차순, false: 내림차순, 기본값: false)"),
					parameterWithName("page").description("페이지 번호 (0부터 시작, 기본값: 0)"),
					parameterWithName("size").description("페이지 크기 (유효한 값: 10, 30, 50, 기본값: 10)")
				)));
	}
}