package takeoff.logistics_service.msa.user.application.service;

import static takeoff.logistics_service.msa.user.application.exception.UserErrorCode.ALREADY_DELETED;
import static takeoff.logistics_service.msa.user.application.exception.UserErrorCode.DELIVERY_MANAGER_NOT_FOUND;
import static takeoff.logistics_service.msa.user.application.exception.UserErrorCode.USERNAME_ALREADY_EXISTS;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.user.application.exception.UserBusinessException;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.repository.UserRepository;
import takeoff.logistics_service.msa.user.domain.service.DeliveryManagerSearchCondition;
import takeoff.logistics_service.msa.user.domain.service.SearchQueryService;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;
import takeoff.logistics_service.msa.user.domain.vo.HubId;
import takeoff.logistics_service.msa.user.presentation.common.dto.PaginationDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetDeliveryManagerListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.DeleteDeliveryManagerResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetDeliveryManagerListInfoDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetDeliveryManagerListResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetDeliveryManagerResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.PatchDeliveryManagerResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.PostDeliveryManagerResponseDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DeliveryManagerServiceImpl implements DeliveryManagerService {

	private final UserRepository userRepository;
	private final SearchQueryService searchQueryService;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public PostDeliveryManagerResponseDto createDeliveryManager(
		PostDeliveryManagerRequestDto requestDto, UserInfoDto userInfoDto) {

		validateRequest(requestDto);
		return PostDeliveryManagerResponseDto.from(
			(DeliveryManager) userRepository.save(requestDto.toEntityWithSequence(
					passwordEncoder.encode(requestDto.password()),
					requestDto.identifier(),
					requestDto)));
	}

	private void validateRequest(PostDeliveryManagerRequestDto requestDto) {
		if (userRepository.existsByUsername(requestDto.username())) {
			throw UserBusinessException.from(USERNAME_ALREADY_EXISTS);
		}
	}

	@Override
	@Transactional
	public PatchDeliveryManagerResponseDto updateDeliveryManager(Long id,
		PatchDeliveryManagerRequestDto requestDto, UserInfoDto userInfoDto) {

		DeliveryManager manager = getDeliveryManager(id);




		if (requestDto.hubId() != null || requestDto.companyId() != null) {
			String newIdentifier =
				requestDto.hubId() != null ? requestDto.hubId() : requestDto.companyId();
			manager.updateIdentifier(newIdentifier);
		}

		manager.updateDeliveryManager(
			requestDto.slackEmail() != null ? requestDto.slackEmail() : manager.getSlackEmail(),
			requestDto.deliverySequence() != null ? DeliverySequence.from(
				requestDto.deliverySequence()) : manager.getDeliverySequence()
		);

		return PatchDeliveryManagerResponseDto.from(manager);
	}

	private DeliveryManager getDeliveryManager(Long id) {
		return userRepository.findDeliveryManagerById(id)
			.orElseThrow(() -> UserBusinessException.from(DELIVERY_MANAGER_NOT_FOUND));
	}

	@Override
	public GetDeliveryManagerResponseDto getDeliveryManagerById(Long id, UserInfoDto userInfoDto) {
		DeliveryManager manager = getDeliveryManager(id);
		return GetDeliveryManagerResponseDto.from(manager);
	}

	@Override
	public GetDeliveryManagerListResponseDto getAllDeliveryManagers(
		GetDeliveryManagerListRequestDto requestDto, UserInfoDto userInfoDto) {
		DeliveryManagerSearchCondition condition = requestDto.toCondition();
		Pageable pageable = requestDto.toPageable();
		Page<DeliveryManager> deliveryManagers = searchQueryService.searchDeliveryManagers(
			condition, pageable);

		List<GetDeliveryManagerListInfoDto> deliveryManagerList = deliveryManagers.getContent()
			.stream()
			.map(GetDeliveryManagerListInfoDto::from)
			.toList();

		return new GetDeliveryManagerListResponseDto(deliveryManagerList,
			PaginationDto.from(deliveryManagers));
	}

	@Override
	@Transactional
	public DeleteDeliveryManagerResponseDto deleteDeliveryManager(Long id, UserInfoDto deletedBy) {
		DeliveryManager manager = getDeliveryManager(id);

		if (manager.isDeleted()) {
			throw UserBusinessException.from(ALREADY_DELETED);
		}
		manager.deleteDeliveryManager(deletedBy.userId());
		return DeleteDeliveryManagerResponseDto.from(manager.getId());
	}

	@Override
	public List<GetDeliveryManagerListInfoDto> getCompanyDeliveryManagersByHubId(UUID hubId,
		UserInfoDto userInfoDto) {
		return userRepository.findAllCompanyDeliveryManagersByHubId(HubId.from(hubId)).stream()
			.map(GetDeliveryManagerListInfoDto::from)
			.toList();
	}

	@Override
	public List<GetDeliveryManagerListInfoDto> getAllHubDeliveryManagers(UserInfoDto userInfoDto) {
		return userRepository.findAllHubDeliveryManagers().stream()
			.map(GetDeliveryManagerListInfoDto::from)
			.toList();
	}
}
