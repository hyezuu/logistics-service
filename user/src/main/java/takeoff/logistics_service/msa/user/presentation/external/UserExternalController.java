package takeoff.logistics_service.msa.user.presentation.external;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.user.application.service.UserService;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetUserListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchUserRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostSignupRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserExternalController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<PostSignupResponseDto> signup(
            @Valid @RequestBody PostSignupRequestDto requestDto
    ) {
        return ResponseEntity.ok(userService.signup(requestDto));
    }

    @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER, UserRole.COMPANY_MANAGER, UserRole.COMPANY_DELIVERY_MANAGER, UserRole.HUB_DELIVERY_MANAGER})
    @GetMapping("/{userId}")
    public ResponseEntity<GetUserResponseDto> getUser(
            @PathVariable Long userId,
            @UserInfo UserInfoDto userInfoDto
            ) {
        GetUserResponseDto responseDto = userService.getUserById(userId, userInfoDto);
        return ResponseEntity.ok(responseDto);
    }

    @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER, UserRole.COMPANY_MANAGER, UserRole.COMPANY_DELIVERY_MANAGER, UserRole.HUB_DELIVERY_MANAGER})
    @PatchMapping("/{userId}")
    public ResponseEntity<PatchUserResponseDto> updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid PatchUserRequestDto requestDto,
            @UserInfo UserInfoDto userInfoDto
    ) {
        PatchUserResponseDto responseDto = userService.updateUser(userId, requestDto, userInfoDto);
        return ResponseEntity.ok(responseDto);
    }

    @RoleCheck(roles = {UserRole.MASTER_ADMIN})
    @DeleteMapping("/{userId}")
    public ResponseEntity<DeleteUserResponseDto> deleteUser(
            @PathVariable Long userId,
            @UserInfo UserInfoDto userInfoDto
    ) {
        DeleteUserResponseDto responseDto = userService.deleteUser(userId, userInfoDto);
        return ResponseEntity.ok(responseDto);
    }

    @RoleCheck(roles = {UserRole.MASTER_ADMIN})
    @GetMapping
    public ResponseEntity<GetUserListResponseDto> getUsers(
            @ModelAttribute GetUserListRequestDto requestDto,
            @UserInfo UserInfoDto userInfoDto
    ) {
        return ResponseEntity.ok(userService.getAllUsers(requestDto, userInfoDto));
    }
}
