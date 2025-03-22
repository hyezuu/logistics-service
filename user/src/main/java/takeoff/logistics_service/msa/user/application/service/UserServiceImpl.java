package takeoff.logistics_service.msa.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.user.domain.entity.CompanyManager;
import takeoff.logistics_service.msa.user.domain.entity.HubManager;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.entity.UserRole;
import takeoff.logistics_service.msa.user.domain.repository.UserRepository;
import takeoff.logistics_service.msa.user.domain.service.SearchQueryService;
import takeoff.logistics_service.msa.user.domain.service.UserSearchCondition;
import takeoff.logistics_service.msa.user.presentation.common.dto.PaginationDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.*;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GetUserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId=" + id));
        return GetUserResponseDto.from(user);
    }

    @Override
    @Transactional
    public PatchUserResponseDto updateUser(Long id, PatchUserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId=" + id));
        user.updateUserInfo(
                requestDto.username() != null ? requestDto.username() : user.getUsername(),
                requestDto.slackEmail() != null ? requestDto.slackEmail() : user.getSlackEmail()
        );
        return PatchUserResponseDto.from(user);
    }

    @Override
    @Transactional
    public DeleteUserResponseDto deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (user.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
        }
        user.delete();
        return DeleteUserResponseDto.from(user);
    }

    @Override
    public GetUserListResponseDto getAllUsers(GetUserListRequestDto requestDto) {
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
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return UserValidationResponseDto.from(user);
    }

    @Override
    public List<GetUserListInfoDto> getUsersByCompanyManagerId(Long managerId) {
        CompanyManager manager = userRepository.findCompanyManagerById(managerId)
                .orElseThrow(() -> new IllegalArgumentException("회사 매니저를 찾을 수 없습니다."));

        UUID companyId = manager.getCompanyId().getCompanyIdentifier();
        return userRepository.findAllByCompanyId(companyId).stream()
                .map(GetUserListInfoDto::from)
                .toList();
    }

    @Override
    public List<GetUserListInfoDto> getUsersByHubManagerId(Long managerId) {
        HubManager manager = userRepository.findHubManagerById(managerId)
                .orElseThrow(() -> new IllegalArgumentException("허브 매니저를 찾을 수 없습니다."));

        UUID hubId = manager.getHubId().getHubIdentifier();
        return userRepository.findAllByHubId(hubId).stream()
                .map(GetUserListInfoDto::from)
                .toList();
    }
}
