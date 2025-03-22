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
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;
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
        boolean isDuplicate = userRepository.findByUsername(requestDto.username())
                .filter(user -> user.getRole().equals(requestDto.role()))
                .isPresent();

        if (isDuplicate) {
            throw new IllegalArgumentException("해당 사용자 이름과 역할이 이미 존재합니다: " + requestDto.username());
        }
        int nextSequence = 0;
        UUID hubId = UUID.fromString(requestDto.identifier());

        if (requestDto.deliveryManagerType() == DeliveryManagerType.COMPANY_DELIVERY_MANAGER) {
            nextSequence = userRepository.countCompanyDeliveryManagersByHubId(hubId);
        } else if (requestDto.deliveryManagerType() == DeliveryManagerType.HUB_DELIVERY_MANAGER) {
            nextSequence = userRepository.countHubDeliveryManagersByHubId(hubId);
        } else {
            throw new IllegalArgumentException("지원하지 않는 배송 관리자 타입입니다.");
        }
        if (nextSequence >= 10) {
            throw new IllegalStateException("해당 허브에는 최대 10명의 배송 담당자만 등록할 수 있습니다.");
        }

        DeliverySequence sequence = DeliverySequence.from(nextSequence);
        DeliveryManager deliveryManager = requestDto.toEntityWithSequence(sequence);
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
    public List<GetDeliveryManagerListInfoDto> getAllHubDeliveryManagers() {
        return userRepository.findAllHubDeliveryManagers().stream()
                .map(GetDeliveryManagerListInfoDto::from)
                .toList();
    }
}
