package ford.relationalMapping.CompanyManagementSystem.service;

import ford.relationalMapping.CompanyManagementSystem.dto.EmployeeDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.EmployeeDetailDTO; // Changed to EmployeeDetailDTO

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDetailDTO getEmployeeById(Long id); // Changed return type
    EmployeeDTO assignAadhaarToEmployee(Long employeeId, Long aadhaarId);
    EmployeeDTO assignProjectsToEmployee(Long employeeId, List<Long> projectIds);
    EmployeeDTO removeProjectsFromEmployee(Long employeeId, List<Long> projectIds);
}
