package takeoff.logistics_service.msa.user.application.service;

import takeoff.logistics_service.msa.user.presentation.dto.request.*;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

public interface UserService {
    PostSignupResponseDto signup(PostSignupRequestDto requestDto);
    GetUserResponseDto getUserById(Long id);
    PatchUserResponseDto updateUser(Long id, PatchUserRequestDto requestDto);
    DeleteUserResponseDto deleteUser(Long id);
    GetUserListResponseDto getAllUsers(GetUserListRequestDto requestDto);
    UserValidationResponseDto validateUser(UserValidationRequestDto requestDto);
}
