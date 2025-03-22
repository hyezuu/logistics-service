package takeoff.logistics_service.msa.user.application.service;

import takeoff.logistics_service.msa.user.presentation.dto.request.GetDeliveryManagerListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

import java.util.List;
import java.util.UUID;

public interface DeliveryManagerService {
    PostDeliveryManagerResponseDto createDeliveryManager(PostDeliveryManagerRequestDto requestDto);
    PatchDeliveryManagerResponseDto updateDeliveryManager(Long id, PatchDeliveryManagerRequestDto requestDto);
    GetDeliveryManagerResponseDto getDeliveryManagerById(Long id);
    GetDeliveryManagerListResponseDto getAllDeliveryManagers(GetDeliveryManagerListRequestDto requestDto);
    DeleteDeliveryManagerResponseDto deleteDeliveryManager(Long id);
    List<GetDeliveryManagerListInfoDto> getCompanyDeliveryManagersByHubId(UUID hubId);
    List<GetDeliveryManagerListInfoDto> getHubDeliveryManagersByHubId(UUID hubId);

}
