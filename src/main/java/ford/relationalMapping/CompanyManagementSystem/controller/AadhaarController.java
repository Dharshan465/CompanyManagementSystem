package ford.relationalMapping.CompanyManagementSystem.controller;

import ford.relationalMapping.CompanyManagementSystem.dto.AadhaarDTO;
import ford.relationalMapping.CompanyManagementSystem.service.AadhaarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aadhaar")
public class AadhaarController {

    private final AadhaarService aadhaarService;

    public AadhaarController(AadhaarService aadhaarService) {
        this.aadhaarService = aadhaarService;
    }

    @PostMapping
    public ResponseEntity<AadhaarDTO> createAadhaar(@Valid @RequestBody AadhaarDTO aadhaarDTO) {
        AadhaarDTO createdAadhaar = aadhaarService.createAadhaar(aadhaarDTO);
        return new ResponseEntity<>(createdAadhaar, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AadhaarDTO> getAadhaarById(@PathVariable Long id) {
        AadhaarDTO aadhaar = aadhaarService.getAadhaarById(id);
        return ResponseEntity.ok(aadhaar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AadhaarDTO> updateAadhaar(@PathVariable Long id, @Valid @RequestBody AadhaarDTO aadhaarDTO) {
        AadhaarDTO updatedAadhaar = aadhaarService.updateAadhaar(id, aadhaarDTO);
        return ResponseEntity.ok(updatedAadhaar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAadhaar(@PathVariable Long id) {
        aadhaarService.deleteAadhaar(id);
        return ResponseEntity.noContent().build();
    }
}
