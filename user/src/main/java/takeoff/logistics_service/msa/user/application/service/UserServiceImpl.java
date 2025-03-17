package takeoff.logistics_service.msa.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.entity.UserRole;
import takeoff.logistics_service.msa.user.domain.repository.UserRepository;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchUserRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostSignupRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostLoginRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;
import takeoff.logistics_service.msa.user.domain.vo.SlackId;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public PostSignupResponseDto signup(PostSignupRequestDto requestDto) {
        validateDuplicateUser(requestDto.email(), requestDto.username(), requestDto.role());

        User user = User.create(
                requestDto.username(),
                requestDto.email(),
                requestDto.password(),
                requestDto.role(),
                new SlackId(UUID.randomUUID())
        );
        User savedUser = userRepository.save(user);

        return PostSignupResponseDto.from(savedUser);
    }

    private void validateDuplicateUser(String email, String username, UserRole role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }
    }

    @Override
    public PostLoginResponseDto login(PostLoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.username())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자입니다."));

        if (!user.getPassword().equals(requestDto.password())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return null;
    }

    @Override
    public GetUserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId=" + userId));

        return GetUserResponseDto.from(user);
    }

    @Override
    public PatchUserResponseDto updateUser(Long userId, PatchUserRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId=" + userId));

        user.updateUserInfo(requestDto.username(), requestDto.email(), requestDto.slackId());

        return PatchUserResponseDto.from(user);
    }

    @Override
    public DeleteUserResponseDto deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (user.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
        }
        user.delete();

        return DeleteUserResponseDto.from(user);
    }

    @Override
    public GetUserListResponseDto getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAllUsers(pageable);
        return GetUserListResponseDto.from(users);
    }

    @Override
    public GetDeliveryTypeResponseDto getDeliveryType(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        if (!user.isDeliveryManager()) {
            throw new IllegalArgumentException("해당 사용자는 배송 담당자가 아닙니다.");
        }

        return GetDeliveryTypeResponseDto.from((DeliveryManager) user);
    }

}
