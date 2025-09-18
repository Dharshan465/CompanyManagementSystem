package ford.relationalMapping.CompanyManagementSystem.service;

import ford.relationalMapping.CompanyManagementSystem.dto.DepartmentDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.DepartmentDetailDTO; // Changed to DepartmentDetailDTO

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDetailDTO getDepartmentById(Long id); // Changed return type
    List<DepartmentDTO> getAllDepartments();
    void deleteDepartment(Long id);
}
