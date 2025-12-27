package cigar.club.backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class UserResponse {
    private String email;
    private String nome;
    private String cognome;
    private LocalDateTime createdAt;
}
