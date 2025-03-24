package takeoff.logistics_service.msa.user.application.service;

import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.*;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

import java.util.List;

public interface UserService {
    PostSignupResponseDto signup(PostSignupRequestDto requestDto);
    GetUserResponseDto getUserById(Long id, UserInfoDto userInfoDto);
    PatchUserResponseDto updateUser(Long id, PatchUserRequestDto requestDto, UserInfoDto userInfoDto);
    DeleteUserResponseDto deleteUser(Long id, UserInfoDto deletedBy);
    GetUserListResponseDto getAllUsers(GetUserListRequestDto requestDto, UserInfoDto userInfoDto);
    UserValidationResponseDto validateUser(UserValidationRequestDto requestDto);
    List<GetManagerListInfoDto> getUsersByCompanyManagerId(Long managerId, UserInfoDto userInfoDto);
    List<GetManagerListInfoDto> getUsersByHubManagerId(Long managerId, UserInfoDto userInfoDto);
}
