package ford.relationalMapping.CompanyManagementSystem.service;

import ford.relationalMapping.CompanyManagementSystem.dto.AadhaarDTO;
import ford.relationalMapping.CompanyManagementSystem.entity.Aadhaar;
import ford.relationalMapping.CompanyManagementSystem.exception.AadhaarException;
import ford.relationalMapping.CompanyManagementSystem.repository.AadhaarRepository;
import ford.relationalMapping.CompanyManagementSystem.service.AadhaarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AadhaarServiceImplementation implements AadhaarService {
    @Autowired
    private final AadhaarRepository aadhaarRepository;

    public AadhaarServiceImplementation(AadhaarRepository aadhaarRepository) {
        this.aadhaarRepository = aadhaarRepository;
    }

    @Override
    @Transactional
    public AadhaarDTO createAadhaar(AadhaarDTO aadhaarDTO) {
        Aadhaar aadhaar = new Aadhaar();
        aadhaar.setAadhaarNumber(aadhaarDTO.getAadhaarNumber());
        Aadhaar savedAadhaar = aadhaarRepository.save(aadhaar);
        return new AadhaarDTO(savedAadhaar.getId(), savedAadhaar.getAadhaarNumber());
    }

    @Override
    @Transactional(readOnly = true)
    public AadhaarDTO getAadhaarById(Long id) {
        Aadhaar aadhaar = aadhaarRepository.findById(id)
                .orElseThrow(() -> new AadhaarException("Aadhaar not found with id: " + id));
        return new AadhaarDTO(aadhaar.getId(), aadhaar.getAadhaarNumber());
    }

    @Override
    @Transactional
    public AadhaarDTO updateAadhaar(Long id, AadhaarDTO aadhaarDTO) {
        Aadhaar aadhaar = aadhaarRepository.findById(id)
                .orElseThrow(() -> new AadhaarException("Aadhaar not found with id: " + id));
        aadhaar.setAadhaarNumber(aadhaarDTO.getAadhaarNumber());
        Aadhaar updatedAadhaar = aadhaarRepository.save(aadhaar);
        return new AadhaarDTO(updatedAadhaar.getId(), updatedAadhaar.getAadhaarNumber());
    }

    @Override
    @Transactional
    public void deleteAadhaar(Long id) {
        if (!aadhaarRepository.existsById(id)) {
            throw new AadhaarException("Aadhaar not found with id: " + id);
        }
        aadhaarRepository.deleteById(id);
    }
}
