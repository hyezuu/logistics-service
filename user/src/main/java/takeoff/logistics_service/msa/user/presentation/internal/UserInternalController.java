package takeoff.logistics_service.msa.user.presentation.internal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import takeoff.logistics_service.msa.user.application.service.UserService;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetUserListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchUserRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostSignupRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.UserValidationRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app/users")
public class UserInternalController {

    private final UserService userService;

    @PostMapping("/validate")
    public UserValidationResponseDto validateUser(@RequestBody UserValidationRequestDto requestDto) {
        return userService.validateUser(requestDto);
    }
}
