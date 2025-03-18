package takeoff.logistics_service.msa.product.product.presentation.external;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.product.product.application.service.ProductService;
import takeoff.logistics_service.msa.product.product.presentation.dto.request.PatchProductRequest;
import takeoff.logistics_service.msa.product.product.presentation.dto.request.PostProductRequest;
import takeoff.logistics_service.msa.product.product.presentation.dto.response.GetProductResponse;
import takeoff.logistics_service.msa.product.product.presentation.dto.response.PatchProductResponse;
import takeoff.logistics_service.msa.product.product.presentation.dto.response.PostProductResponse;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductExternalController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<PostProductResponse> saveProduct(
		@Valid @RequestBody PostProductRequest requestDto) {

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(PostProductResponse
				.from(productService.saveProduct(requestDto.toApplicationDto())));
	}

	@PatchMapping("/{productId}")
	public ResponseEntity<PatchProductResponse> updateProductName(
		@PathVariable UUID productId, @Valid @RequestBody PatchProductRequest requestDto){

		return ResponseEntity.ok(PatchProductResponse
			.from(productService.updateProductName(
				productId, requestDto.toApplicationDto())));
	}

	@GetMapping("/{productId}")
	public ResponseEntity<GetProductResponse> findProduct(@PathVariable UUID productId){

		return ResponseEntity.ok(GetProductResponse.from(productService.findProduct(productId)));
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId){

		productService.deleteProduct(productId);
		return ResponseEntity.noContent().build();
	}
}
