package takeoff.logistics_service.msa.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.entity.UserRole;
import takeoff.logistics_service.msa.user.domain.repository.UserRepository;
import takeoff.logistics_service.msa.user.domain.repository.UserSpecifications;
import takeoff.logistics_service.msa.user.domain.service.UserQueryService;
import takeoff.logistics_service.msa.user.domain.service.UserSearchCondition;
import takeoff.logistics_service.msa.user.presentation.common.dto.UserPaginationDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetUserListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchUserRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostLoginRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostSignupRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserQueryService userQueryService;

    @Override
    @Transactional
    public PostSignupResponseDto signup(PostSignupRequestDto requestDto) {
        validateDuplicateUser(requestDto.slackEmail(), requestDto.username(), requestDto.role());
        User user = requestDto.toEntity();
        User savedUser = userRepository.save(user);
        return PostSignupResponseDto.from(savedUser);
    }

    private void validateDuplicateUser(String slackEmail, String username, UserRole role) {
        if (userRepository.findByEmail(slackEmail).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PostLoginResponseDto login(PostLoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.username())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자입니다."));
        if (!user.getPassword().equals(requestDto.password())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return null;
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
        Page<User> users = userQueryService.searchUsers(condition, pageable);

        List<GetUserListInfoDto> userList = users.getContent().stream()
                .map(GetUserListInfoDto::from)
                .toList();

        return new GetUserListResponseDto(userList, UserPaginationDto.from(users));
    }


}
