package takeoff.logistics_service.msa.user.application.service;

import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetDeliveryManagerListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

import java.util.List;
import java.util.UUID;

public interface DeliveryManagerService {
    PostDeliveryManagerResponseDto createDeliveryManager(PostDeliveryManagerRequestDto requestDto, UserInfoDto userInfoDto);
    PatchDeliveryManagerResponseDto updateDeliveryManager(Long id, PatchDeliveryManagerRequestDto requestDto, UserInfoDto userInfoDto);
    GetDeliveryManagerResponseDto getDeliveryManagerById(Long id, UserInfoDto userInfoDto);
    GetDeliveryManagerListResponseDto getAllDeliveryManagers(GetDeliveryManagerListRequestDto requestDto,  UserInfoDto userInfoDto);
    DeleteDeliveryManagerResponseDto deleteDeliveryManager(Long id, UserInfoDto deletedBy);
    List<GetDeliveryManagerListInfoDto> getCompanyDeliveryManagersByHubId(UUID hubId, UserInfoDto userInfoDto);
    List<GetDeliveryManagerListInfoDto> getAllHubDeliveryManagers(UserInfoDto userInfoDto);

}
