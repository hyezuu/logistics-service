package takeoff.logistics_service.msa.user.presentation.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.user.application.service.UserService;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.presentation.dto.request.UserValidationRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetManagerListInfoDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetManagerListInternalResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.UserValidationResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app/users")
public class UserInternalController {

    private final UserService userService;

    @PostMapping("/validate")
    public UserValidationResponseDto validateUser(@RequestBody UserValidationRequestDto requestDto) {
        return userService.validateUser(requestDto);
    }

    @RoleCheck(roles = {UserRole.COMPANY_MANAGER})
    @GetMapping("/managers/company/users")
    public GetManagerListInternalResponseDto getUsersByCompanyManager(
            @RequestParam Long managerId,
            @UserInfo UserInfoDto userInfoDto
            ) {
        List<GetManagerListInfoDto> list = userService.getUsersByCompanyManagerId(managerId, userInfoDto);
        return GetManagerListInternalResponseDto.from(list);
    }

    @RoleCheck(roles = {UserRole.HUB_MANAGER})
    @GetMapping("/managers/hub/users")
    public GetManagerListInternalResponseDto getUsersByHubManager(
            @RequestParam Long managerId,
            @UserInfo UserInfoDto userInfoDto
            ) {
        List<GetManagerListInfoDto> list = userService.getUsersByHubManagerId(managerId, userInfoDto);
        return GetManagerListInternalResponseDto.from(list);
    }


}
