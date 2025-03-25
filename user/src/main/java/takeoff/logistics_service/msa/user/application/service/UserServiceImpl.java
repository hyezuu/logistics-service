package takeoff.logistics_service.msa.user.application.service;

import static takeoff.logistics_service.msa.user.application.exception.UserErrorCode.ALREADY_DELETED;
import static takeoff.logistics_service.msa.user.application.exception.UserErrorCode.DELIVERY_MANAGER_NOT_FOUND;
import static takeoff.logistics_service.msa.user.application.exception.UserErrorCode.EMAIL_ALREADY_EXISTS;
import static takeoff.logistics_service.msa.user.application.exception.UserErrorCode.INVALID_PASSWORD;
import static takeoff.logistics_service.msa.user.application.exception.UserErrorCode.USERNAME_ALREADY_EXISTS;
import static takeoff.logistics_service.msa.user.application.exception.UserErrorCode.USER_NOT_FOUND;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.user.application.exception.UserBusinessException;
import takeoff.logistics_service.msa.user.domain.entity.CompanyManager;
import takeoff.logistics_service.msa.user.domain.entity.HubManager;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.entity.UserRole;
import takeoff.logistics_service.msa.user.domain.repository.UserRepository;
import takeoff.logistics_service.msa.user.domain.service.SearchQueryService;
import takeoff.logistics_service.msa.user.domain.service.UserSearchCondition;
import takeoff.logistics_service.msa.user.presentation.common.dto.PaginationDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetUserListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchUserRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostSignupRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.UserValidationRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.DeleteUserResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetManagerListInfoDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetUserListInfoDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetUserListResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetUserResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.PatchUserResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.PostSignupResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.UserValidationResponseDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SearchQueryService searchQueryService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public PostSignupResponseDto signup(PostSignupRequestDto requestDto) {
        validateDuplicateUser(requestDto.slackEmail(), requestDto.username(), requestDto.role());
        String encodedPassword = passwordEncoder.encode(requestDto.password());

        User user = requestDto.toEntity(encodedPassword);
        User savedUser = userRepository.save(user);
        return PostSignupResponseDto.from(savedUser);
    }

    private void validateDuplicateUser(String slackEmail, String username, UserRole role) {
        if (userRepository.findBySlackEmail(slackEmail).isPresent()) {
            throw UserBusinessException.from(EMAIL_ALREADY_EXISTS);
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw UserBusinessException.from(USERNAME_ALREADY_EXISTS);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GetUserResponseDto getUserById(Long id, UserInfoDto userInfoDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> UserBusinessException.from(USER_NOT_FOUND));
        return GetUserResponseDto.from(user);
    }

    @Override
    @Transactional
    public PatchUserResponseDto updateUser(Long id, PatchUserRequestDto requestDto, UserInfoDto userInfoDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> UserBusinessException.from(USER_NOT_FOUND));
        user.updateUserInfo(
                requestDto.username() != null ? requestDto.username() : user.getUsername(),
                requestDto.slackEmail() != null ? requestDto.slackEmail() : user.getSlackEmail()
        );
        return PatchUserResponseDto.from(user);
    }

    @Override
    @Transactional
    public DeleteUserResponseDto deleteUser(Long id, UserInfoDto deletedBy) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> UserBusinessException.from(USER_NOT_FOUND));
        if (user.isDeleted()) {
            throw UserBusinessException.from(ALREADY_DELETED);
        }
        user.delete(deletedBy.userId());
        return DeleteUserResponseDto.from(user);
    }

    @Override
    public GetUserListResponseDto getAllUsers(GetUserListRequestDto requestDto, UserInfoDto userInfoDto) {
        UserSearchCondition condition = requestDto.toCondition();
        Pageable pageable = requestDto.toPageable();
        Page<User> users = searchQueryService.searchUsers(condition, pageable);

        List<GetUserListInfoDto> userList = users.getContent().stream()
                .map(GetUserListInfoDto::from)
                .toList();

        return new GetUserListResponseDto(userList, PaginationDto.from(users));
    }

    @Override
    @Transactional(readOnly = true)
    public UserValidationResponseDto validateUser(UserValidationRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.username())
                .orElseThrow(() -> UserBusinessException.from(USERNAME_ALREADY_EXISTS));
        log.info(user.getUsername());
        log.info(String.valueOf(user.getId()));
        log.info(String.valueOf(user.getRole()));
        log.info("실행--------");

        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw UserBusinessException.from(INVALID_PASSWORD);
        }

        return UserValidationResponseDto.from(user);
    }

    @Override
    public List<GetManagerListInfoDto> getUsersByCompanyManagerId(Long managerId, UserInfoDto userInfoDto) {
        CompanyManager manager = userRepository.findCompanyManagerById(managerId)
                .orElseThrow(() -> UserBusinessException.from(DELIVERY_MANAGER_NOT_FOUND));
        return userRepository.findAllEmployeesByCompanyId(manager.getCompanyId()).stream()
                .map(GetManagerListInfoDto::from)
                .toList();
    }

    @Override
    public List<GetManagerListInfoDto> getUsersByHubManagerId(Long managerId, UserInfoDto userInfoDto) {
        HubManager manager = userRepository.findHubManagerById(managerId)
                .orElseThrow(() -> UserBusinessException.from(DELIVERY_MANAGER_NOT_FOUND));
        return userRepository.findAllEmployeesByHubId(manager.getHubId()).stream()
                .map(GetManagerListInfoDto::from)
                .toList();
    }
}
