package cigar.club.backend.service;

import cigar.club.backend.models.User;
import cigar.club.backend.repository.UserRepository;
import cigar.club.backend.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
