package ford.relationalMapping.CompanyManagementSystem.service;

import ford.relationalMapping.CompanyManagementSystem.dto.AadhaarDTO;

public interface AadhaarService {
    AadhaarDTO createAadhaar(AadhaarDTO aadhaarDTO);
    AadhaarDTO getAadhaarById(Long id);
    AadhaarDTO updateAadhaar(Long id, AadhaarDTO aadhaarDTO);
    void deleteAadhaar(Long id);
}
