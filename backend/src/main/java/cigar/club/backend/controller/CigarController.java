package cigar.club.backend.controller;

import cigar.club.backend.models.Cigar;
import cigar.club.backend.service.CigarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cigar")
public class CigarController {

    private CigarService cigarService;

    public CigarController(CigarService cigarService) {
        this.cigarService = cigarService;
    }

    @GetMapping("")
    public ResponseEntity<List<Cigar>> getCigar() {
        return ResponseEntity.ok(cigarService.getCigars());
    }

    @GetMapping("/{barCode}")
    public ResponseEntity<Cigar> getCigarById(@PathVariable String barCode) {
        return ResponseEntity.ok(cigarService.getCigarByBarCode(barCode)
                .orElseThrow()
        );

    }
}
