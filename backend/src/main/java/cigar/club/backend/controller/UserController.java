package cigar.club.backend.controller;

import cigar.club.backend.dto.UserResponse;
import cigar.club.backend.models.User;
import cigar.club.backend.service.UserService;
import cigar.club.backend.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserController{

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@RequestHeader("Authorization") String authHeader) {

        // Rimuovi "Bearer " dal token
        String token = authHeader.substring(7);

        // Estrai l'email dal token
        String email = jwtUtil.extractEmail(token);
        log.info("getCurrentUser email: {}", email);

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(email);
        userResponse.setCognome(user.getCognome());
        userResponse.setNome(user.getNome());
        userResponse.setCreatedAt(user.getCreatedAt());
        return ResponseEntity.ok(userResponse);
    }
}
