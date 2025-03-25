package takeoff.logistics_service.msa.auth.presentation.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.auth.application.service.AuthService;
import takeoff.logistics_service.msa.auth.application.service.TokenService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String userId) {
        authService.logout(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestParam String refreshToken) {
        Optional<String> newAccessToken = tokenService.refreshAccessToken(refreshToken);
        return newAccessToken.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("유효하지 않은 RefreshToken 입니다."));
    }
}
