package cigar.club.backend.repository;

import cigar.club.backend.models.Cigar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CigarRepository extends JpaRepository<Cigar, Long> {
    Optional<Cigar> findCigarById(Long id);
    Optional<Cigar> findByBarCode(String barCode);
}
