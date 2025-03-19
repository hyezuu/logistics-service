package takeoff.logistics_service.msa.hub.hub.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.HubIds;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
public record GetRouteRequest(@NotNull HubIds hubIds) {

}
