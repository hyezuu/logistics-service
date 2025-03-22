package takeoff.logistics_service.msa.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.repository.UserRepository;
import takeoff.logistics_service.msa.user.domain.service.DeliveryManagerSearchCondition;
import takeoff.logistics_service.msa.user.domain.service.SearchQueryService;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;
import takeoff.logistics_service.msa.user.presentation.common.dto.PaginationDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetDeliveryManagerListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryManagerServiceImpl implements DeliveryManagerService {

    private final UserRepository userRepository;
    private final SearchQueryService searchQueryService;

    @Override
    @Transactional
    public PostDeliveryManagerResponseDto createDeliveryManager(PostDeliveryManagerRequestDto requestDto) {
        if(userRepository.findByUsername(requestDto.username()).isEmpty()){
            throw new IllegalArgumentException("해당 사용자 이름이 존재하지 않습니다: " + requestDto.username());
        }
        DeliveryManager deliveryManager = requestDto.toEntity();
        userRepository.save(deliveryManager);
        return PostDeliveryManagerResponseDto.from(deliveryManager);
    }

    @Override
    @Transactional
    public PatchDeliveryManagerResponseDto updateDeliveryManager(Long id, PatchDeliveryManagerRequestDto requestDto) {
        DeliveryManager manager = userRepository.findDeliveryManagerById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 배송 관리자를 찾을 수 없습니다. userId=" + id));

        // ID 변경을 `DeliveryManager` 내부에서 처리하도록 위임
        if (requestDto.hubId() != null || requestDto.companyId() != null) {
            String newIdentifier = requestDto.hubId() != null ? requestDto.hubId() : requestDto.companyId();
            manager.updateIdentifier(newIdentifier);
        }

        manager.updateDeliveryManager(
                requestDto.slackEmail() != null ? requestDto.slackEmail() : manager.getSlackEmail(),
                requestDto.deliverySequence() != null ? DeliverySequence.from(requestDto.deliverySequence()) : manager.getDeliverySequence()
        );

        return PatchDeliveryManagerResponseDto.from(manager);
    }

    @Override
    public GetDeliveryManagerResponseDto getDeliveryManagerById(Long id) {
        DeliveryManager manager = userRepository.findDeliveryManagerById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 배송 관리자를 찾을 수 없습니다. userId=" + id));
        return GetDeliveryManagerResponseDto.from(manager);
    }

    @Override
    public GetDeliveryManagerListResponseDto getAllDeliveryManagers(GetDeliveryManagerListRequestDto requestDto) {
        DeliveryManagerSearchCondition condition = requestDto.toCondition();
        Pageable pageable = requestDto.toPageable();
        Page<DeliveryManager> deliveryManagers = searchQueryService.searchDeliveryManagers(condition, pageable);

        List<GetDeliveryManagerListInfoDto> deliveryManagerList = deliveryManagers.getContent().stream()
                .map(GetDeliveryManagerListInfoDto::from)
                .toList();

        return new GetDeliveryManagerListResponseDto(deliveryManagerList, PaginationDto.from(deliveryManagers));
    }

    @Override
    @Transactional
    public DeleteDeliveryManagerResponseDto deleteDeliveryManager(Long id) {
        DeliveryManager manager = userRepository.findDeliveryManagerById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 배송 관리자를 찾을 수 없습니다. userID=" + id));

        if (manager.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 배송 관리자입니다.");
        }
        manager.deleteDeliveryManager();
        return DeleteDeliveryManagerResponseDto.from(manager.getId());
    }

    @Override
    public List<GetDeliveryManagerListInfoDto> getCompanyDeliveryManagersByHubId(UUID hubId) {
        return userRepository.findAllCompanyDeliveryManagersByHubId(hubId).stream()
                .map(GetDeliveryManagerListInfoDto::from)
                .toList();
    }

    @Override
    public List<GetDeliveryManagerListInfoDto> getHubDeliveryManagersByHubId(UUID hubId) {
        return userRepository.findAllHubDeliveryManagersByHubId(hubId).stream()
                .map(GetDeliveryManagerListInfoDto::from)
                .toList();
    }


}
