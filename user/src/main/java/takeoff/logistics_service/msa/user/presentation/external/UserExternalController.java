package takeoff.logistics_service.msa.user.presentation.external;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import takeoff.logistics_service.msa.user.application.service.UserService;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetUserListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchUserRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostSignupRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

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
            @PathVariable Long userId) {
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
