package takeoff.logistics_service.msa.product.product.domain.command;

import java.util.UUID;

public record CreateProduct(String name, UUID companyId) {
}
