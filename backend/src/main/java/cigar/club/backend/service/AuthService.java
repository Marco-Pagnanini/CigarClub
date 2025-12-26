package cigar.club.backend.service;

import cigar.club.backend.dto.AuthResponse;
import cigar.club.backend.dto.LoginRequest;
import cigar.club.backend.dto.RegisterRequest;
import cigar.club.backend.models.RefreshToken;
import cigar.club.backend.models.User;
import cigar.club.backend.repository.RefreshTokenRepository;
import cigar.club.backend.repository.UserRepository;
import cigar.club.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    // Login
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenziali non valide"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenziali non valide"
            );

        }

        String accessToken = jwtUtil.generateAccessToken(user.getEmail());
        String refreshToken = createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken, user.getEmail());
    }

    // Registrazione
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenziali errore"
            );

        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNome(request.getNome());
        user.setCognome(request.getCognome());
        user.setCreatedAt(LocalDateTime.now());

        user = userRepository.save(user);

        String accessToken = jwtUtil.generateAccessToken(user.getEmail());
        String refreshToken = createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken, user.getEmail());
    }

    // Refresh Token
    public AuthResponse refreshToken(String refreshTokenString) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenString)
                .orElseThrow(() -> new RuntimeException("Refresh token non valido"));

        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token scaduto");
        }

        String newAccessToken = jwtUtil.generateAccessToken(refreshToken.getUser().getEmail());

        // Opzionale: ruota anche il refresh token per maggiore sicurezza
        String newRefreshToken = createRefreshToken(refreshToken.getUser());
        refreshTokenRepository.delete(refreshToken);

        return new AuthResponse(newAccessToken, newRefreshToken, refreshToken.getUser().getEmail());
    }

    // Crea e salva refresh token
    private String createRefreshToken(User user) {
        // Cancella eventuali refresh token esistenti per questo utente
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(jwtUtil.generateRefreshToken());
        refreshToken.setCreatedAt(LocalDateTime.now());
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000));

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    // Logout
    public void logout(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}