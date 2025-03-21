package takeoff.logistics_service.msa.user.presentation.external;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import takeoff.logistics_service.msa.user.application.service.UserService;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetUserListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchUserRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostSignupRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.UserValidationRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserExternalController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<PostSignupResponseDto> signup(
            @Valid @RequestBody PostSignupRequestDto requestDto) {
        return ResponseEntity.ok(userService.signup(requestDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetUserResponseDto> getUser(
            @PathVariable Long userId,
            @RequestHeader(value = "X-User-Id", required = false) String requestUserId,
            @RequestHeader(value = "X-User-Role", required = false) String requestUserRole
    ) {
        log.info("API Gateway에서 전달된 X-User-Id={}, X-User-Role={}", requestUserId, requestUserRole);

        GetUserResponseDto responseDto = userService.getUserById(userId);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<PatchUserResponseDto> updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid PatchUserRequestDto requestDto
    ) {
        PatchUserResponseDto responseDto = userService.updateUser(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<DeleteUserResponseDto> deleteUser(@PathVariable Long userId) {
        DeleteUserResponseDto responseDto = userService.deleteUser(userId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<GetUserListResponseDto> getUsers(
            @ModelAttribute GetUserListRequestDto requestDto) {
        return ResponseEntity.ok(userService.getAllUsers(requestDto));
    }
}
