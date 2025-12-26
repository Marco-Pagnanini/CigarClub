package cigar.club.backend.service;

import cigar.club.backend.models.Cigar;
import cigar.club.backend.repository.CigarRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CigarService {
    private CigarRepository cigarRepository;

    public CigarService(CigarRepository cigarRepository) {
        this.cigarRepository = cigarRepository;
    }

    public List<Cigar> getCigars() {
        return cigarRepository.findAll();
    }

    public Optional<Cigar> getCigarByBarCode(String barCode) {
        return cigarRepository.findByBarCode(barCode);
    }


}
