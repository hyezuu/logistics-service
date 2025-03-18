package takeoff.logistics_service.msa.user.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.DeleteDeliveryManagerResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetDeliveryManagerResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.PatchDeliveryManagerResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.PostDeliveryManagerResponseDto;

public interface DeliveryManagerService {
    PostDeliveryManagerResponseDto createDeliveryManager(PostDeliveryManagerRequestDto requestDto);
    PatchDeliveryManagerResponseDto updateDeliveryManager(Long id, PatchDeliveryManagerRequestDto requestDto);
    GetDeliveryManagerResponseDto getDeliveryManagerById(Long id);
    Page<PostDeliveryManagerResponseDto> getAllDeliveryManagers(Pageable pageable);
    DeleteDeliveryManagerResponseDto deleteDeliveryManager(Long id);

}
