package ford.relationalMapping.CompanyManagementSystem.service;

import ford.relationalMapping.CompanyManagementSystem.dto.DepartmentDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.DepartmentDetailDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.EmployeeBasicDTO; // Changed to EmployeeBasicDTO
import ford.relationalMapping.CompanyManagementSystem.entity.Department;
import ford.relationalMapping.CompanyManagementSystem.exception.DepartmentException;
import ford.relationalMapping.CompanyManagementSystem.repository.DepartmentRepository;
import ford.relationalMapping.CompanyManagementSystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImplementation implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    @Autowired
    public DepartmentServiceImplementation(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setLocation(departmentDTO.getLocation());
        Department savedDepartment = departmentRepository.save(department);
        return new DepartmentDTO(savedDepartment.getId(), savedDepartment.getName(), savedDepartment.getLocation());
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDetailDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentException("Department not found with id: " + id));

        // Accessing employees list here triggers lazy loading within the @Transactional context
        List<EmployeeBasicDTO> employees = department.getEmployees().stream()
                .map(employee -> new EmployeeBasicDTO(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail()))
                .collect(Collectors.toList());

        return new DepartmentDetailDTO(department.getId(), department.getName(), department.getLocation(), employees);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(department -> new DepartmentDTO(department.getId(), department.getName(), department.getLocation()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new DepartmentException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
