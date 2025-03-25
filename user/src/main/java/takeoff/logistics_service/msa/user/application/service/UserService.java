package takeoff.logistics_service.msa.user.application.service;

import java.util.List;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetUserListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchUserRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostSignupRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.UserValidationRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.DeleteUserResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetManagerListInfoDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetUserListResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetUserResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.PatchUserResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.PostSignupResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.UserValidationResponseDto;

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
