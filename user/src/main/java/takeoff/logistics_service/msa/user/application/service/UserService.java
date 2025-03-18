package takeoff.logistics_service.msa.user.application.service;

import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchUserRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostSignupRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostLoginRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

public interface UserService {
    PostSignupResponseDto signup(PostSignupRequestDto requestDto);
    PostLoginResponseDto login(PostLoginRequestDto requestDto);
    GetUserResponseDto getUserById(Long userId);
    PatchUserResponseDto updateUser(Long userId, PatchUserRequestDto requestDto);
    DeleteUserResponseDto deleteUser(Long userId);
    GetUserListResponseDto getAllUsers(Pageable pageable);
}
