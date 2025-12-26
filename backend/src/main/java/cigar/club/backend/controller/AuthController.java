package cigar.club.backend.controller;

import cigar.club.backend.dto.AuthResponse;
import cigar.club.backend.dto.LoginRequest;
import cigar.club.backend.dto.RefreshTokenRequest;
import cigar.club.backend.dto.RegisterRequest;
import cigar.club.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        AuthResponse registerResponse = authService.register(request);
        if (registerResponse == null) {
            return ResponseEntity.badRequest().body("Email già presente");
        }
        return ResponseEntity.ok(registerResponse);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse loginRequest = authService.login(request);
        if(loginRequest == null) {
            return ResponseEntity.badRequest().body(loginRequest);
        }
        return ResponseEntity.ok(loginRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok().build();
    }
}