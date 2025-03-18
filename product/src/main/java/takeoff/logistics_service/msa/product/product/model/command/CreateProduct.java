package takeoff.logistics_service.msa.product.product.model.command;

import java.util.UUID;

public record CreateProduct(String name, UUID companyId) {
}
