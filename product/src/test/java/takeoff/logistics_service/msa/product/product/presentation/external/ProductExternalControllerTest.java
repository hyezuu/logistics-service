package takeoff.logistics_service.msa.product.product.presentation.external;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
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

	private final String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
		+ ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6M"
		+ "TUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";

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
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andDo(document("상품 생성 - 필수 필드 입력",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Product-External")
					.summary("상품 생성")
					.description("상품을 생성하기 위한 엔드포인트입니다.")
					.requestFields(
						fieldWithPath("name").description("상품 이름"),
						fieldWithPath("companyId").description("업체 ID"),
						fieldWithPath("hubId").description("허브 ID"),
						fieldWithPath("quantity").description("초기 재고 수량 (null 가능, 기본값 0)"))
					.build()
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
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andDo(document("상품 생성 - 기본 재고 수량",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Product-External")
					.summary("상품 생성 요청")
					.description("초기 재고 수량을 지정하지 않고 상품을 생성합니다. 기본값은 0입니다.")
					.requestFields(
						fieldWithPath("name").description("상품 이름"),
						fieldWithPath("companyId").description("업체 ID"),
						fieldWithPath("hubId").description("허브 ID"),
						fieldWithPath("quantity").description("초기 재고 수량 (null 가능, 기본값 0)")
					)
					.build()
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
				.header(HttpHeaders.AUTHORIZATION, token)
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
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(updateRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").value(productId.toString()))
			.andExpect(jsonPath("$.name").value("프리미엄 유기농 바나나"))
			.andDo(document("상품 이름 수정",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Product-External")
					.summary("상품 이름 수정")
					.description("상품 이름을 수정합니다")
					.pathParameters(
						parameterWithName("productId").description("수정할 상품 ID")
					)
					.requestFields(
						fieldWithPath("name").description("변경할 상품 이름")
					)
					.build()
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
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(createRequest)))
			.andExpect(status().isCreated())
			.andReturn()
			.getResponse()
			.getContentAsString();

		UUID productId = UUID.fromString(
			objectMapper.readTree(createResponse).get("productId").asText());

		// when & then
		mockMvc.perform(get("/api/v1/products/{productId}", productId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").value(productId.toString()))
			.andExpect(jsonPath("$.name").value("유기농 바나나"))
			.andDo(document("상품 정보 조회",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Product-External")
					.summary("상품 정보 조회")
					.description("상품 정보를 조회합니다")
					.pathParameters(
						parameterWithName("productId").description("조회할 상품 ID")
					)
					.build()
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
				.header(HttpHeaders.AUTHORIZATION, token)
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
				.header(HttpHeaders.AUTHORIZATION, token)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent())
			.andDo(document("상품 삭제",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Product-External")
					.summary("상품 삭제")
					.description("상품을 삭제합니다")
					.pathParameters(
						parameterWithName("productId").description("삭제할 상품 ID")
					)
					.build()
				)));
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
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(request1)))
			.andExpect(status().isCreated());

		// 두 번째 상품 생성
		PostProductRequest request2 = new PostProductRequest("프리미엄 사과", companyId, hubId, 50);
		mockMvc.perform(post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-User-Id", "1")
				.header("X-User-Role", "MASTER_ADMIN")
				.header(HttpHeaders.AUTHORIZATION, token)
				.content(objectMapper.writeValueAsString(request2)))
			.andExpect(status().isCreated());

		// when & then - 기본 검색
		mockMvc.perform(get("/api/v1/products/search")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("상품 검색 - 기본",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Product-External")
					.summary("상품 검색 - 기본")
					.description("상품을 검색합니다. 기본 설정은 최신순, 10개씩 페이징입니다.")
					.build()
				)));

		// 이름으로 검색
		mockMvc.perform(get("/api/v1/products/search")
				.param("name", "바나나")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("상품 이름으로 검색",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Product-External")
					.summary("상품 이름으로 검색")
					.description("상품 이름으로 검색합니다. 부분 일치 검색이 가능합니다.")
					.queryParameters(
						parameterWithName("name").description("검색할 상품 이름")
					)
					.build()
				)));

		// 업체 ID로 검색
		mockMvc.perform(get("/api/v1/products/search")
				.param("companyId", companyId.toString())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("상품 업체 ID로 검색",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Product-External")
					.summary("업체 ID로 상품 검색")
					.description("업체 ID로 검색합니다. 해당 업체의 모든 상품을 검색합니다.")
					.queryParameters(
						parameterWithName("companyId").description("검색할 업체 ID")
					)
					.build()
				)));

		// 정렬 및 페이징 검색
		mockMvc.perform(get("/api/v1/products/search")
				.param("sortBy", "name")
				.param("isAsc", "true")
				.param("page", "0")
				.param("size", "10")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("상품 정렬 및 페이징 검색",
				preprocessRequest(Preprocessors.prettyPrint()),
				preprocessResponse(Preprocessors.prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag("Product-External")
					.summary("상품 정렬 및 페이징 검색")
					.description("정렬 및 페이징 옵션을 적용하여 검색합니다.")
					.queryParameters(
						parameterWithName("sortBy").description("정렬 기준 필드 (기본값: createdAt)"),
						parameterWithName("isAsc").description(
							"오름차순 여부 (true: 오름차순, false: 내림차순, 기본값: false)"),
						parameterWithName("page").description("페이지 번호 (0부터 시작, 기본값: 0)"),
						parameterWithName("size").description("페이지 크기 (유효한 값: 10, 30, 50, 기본값: 10)")
					)
					.build()
				)));
	}
}