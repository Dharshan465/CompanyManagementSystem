package ford.relationalMapping.CompanyManagementSystem.service;

import ford.relationalMapping.CompanyManagementSystem.dto.*;
import ford.relationalMapping.CompanyManagementSystem.entity.Aadhaar;
import ford.relationalMapping.CompanyManagementSystem.entity.Department;
import ford.relationalMapping.CompanyManagementSystem.entity.Employee;
import ford.relationalMapping.CompanyManagementSystem.entity.Project;
import ford.relationalMapping.CompanyManagementSystem.exception.AadhaarException;
import ford.relationalMapping.CompanyManagementSystem.exception.DepartmentException;
import ford.relationalMapping.CompanyManagementSystem.exception.EmployeeException;
import ford.relationalMapping.CompanyManagementSystem.exception.ProjectException;
import ford.relationalMapping.CompanyManagementSystem.repository.AadhaarRepository;
import ford.relationalMapping.CompanyManagementSystem.repository.DepartmentRepository;
import ford.relationalMapping.CompanyManagementSystem.repository.EmployeeRepository;
import ford.relationalMapping.CompanyManagementSystem.repository.ProjectRepository;
import ford.relationalMapping.CompanyManagementSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AadhaarRepository aadhaarRepository;
    private final ProjectRepository projectRepository;
    @Autowired
    public EmployeeServiceImplementation(EmployeeRepository employeeRepository,
                                         DepartmentRepository departmentRepository,
                                         AadhaarRepository aadhaarRepository,
                                         ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.aadhaarRepository = aadhaarRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new DepartmentException("Department not found with id: " + employeeDTO.getDepartmentId()));

        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment(department);

        if (employeeDTO.getAadhaarId() != null) {
            Aadhaar aadhaar = aadhaarRepository.findById(employeeDTO.getAadhaarId())
                    .orElseThrow(() -> new AadhaarException("Aadhaar not found with id: " + employeeDTO.getAadhaarId()));
            employee.setAadhaar(aadhaar);
        }

        Employee savedEmployee = employeeRepository.save(employee);

        if (employeeDTO.getProjectIds() != null && !employeeDTO.getProjectIds().isEmpty()) {
            employeeDTO.getProjectIds().forEach(projectId -> {
                Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new ProjectException("Project not found with id: " + projectId));
                savedEmployee.addProject(project); // Ensure bidirectional update
            });
            employeeRepository.save(savedEmployee); // Save again to update projects
        }

        return new EmployeeDTO(
                savedEmployee.getId(),
                savedEmployee.getFirstName(),
                savedEmployee.getLastName(),
                savedEmployee.getEmail(),
                savedEmployee.getSalary(),
                savedEmployee.getDepartment().getId(),
                savedEmployee.getAadhaar() != null ? savedEmployee.getAadhaar().getId() : null,
                savedEmployee.getProjects().stream().map(Project::getId).collect(Collectors.toList())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDetailDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeException("Employee not found with id: " + id));

        // Trigger lazy loading for Department
        DepartmentDTO departmentDTO = new DepartmentDTO(
                employee.getDepartment().getId(),
                employee.getDepartment().getName(),
                employee.getDepartment().getLocation()
        );

        // Trigger lazy loading for Aadhaar
        AadhaarDTO aadhaarDTO = null;
        if (employee.getAadhaar() != null) {
            Aadhaar aadhaar = employee.getAadhaar();
            aadhaarDTO = new AadhaarDTO(aadhaar.getId(), aadhaar.getAadhaarNumber());
        }

        // Trigger lazy loading for Projects (EAGER on Project side, but LAZY on Employee side due to mappedBy)
        List<ProjectDTO> projectDTOs = employee.getProjects().stream()
                .map(project -> new ProjectDTO(project.getId(), project.getName(), project.getBudget()))
                .collect(Collectors.toList());

        return new EmployeeDetailDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getSalary(),
                departmentDTO,
                aadhaarDTO,
                projectDTOs
        );
    }

    @Override
    @Transactional
    public EmployeeDTO assignAadhaarToEmployee(Long employeeId, Long aadhaarId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeException("Employee not found with id: " + employeeId));
        Aadhaar aadhaar = aadhaarRepository.findById(aadhaarId)
                .orElseThrow(() -> new AadhaarException("Aadhaar not found with id: " + aadhaarId));

        if (employee.getAadhaar() != null && !employee.getAadhaar().getId().equals(aadhaarId)) {
            throw new DataIntegrityViolationException("Employee already has an Aadhaar assigned.");
        }
        if (aadhaar.getEmployee() != null && !aadhaar.getEmployee().getId().equals(employeeId)) {
            throw new DataIntegrityViolationException("Aadhaar is already assigned to another employee.");
        }

        employee.setAadhaar(aadhaar);
        Employee updatedEmployee = employeeRepository.save(employee);

        return new EmployeeDTO(
                updatedEmployee.getId(),
                updatedEmployee.getFirstName(),
                updatedEmployee.getLastName(),
                updatedEmployee.getEmail(),
                updatedEmployee.getSalary(),
                updatedEmployee.getDepartment().getId(),
                updatedEmployee.getAadhaar() != null ? updatedEmployee.getAadhaar().getId() : null,
                updatedEmployee.getProjects().stream().map(Project::getId).collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    public EmployeeDTO assignProjectsToEmployee(Long employeeId, List<Long> projectIds) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeException("Employee not found with id: " + employeeId));

        projectIds.forEach(projectId -> {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ProjectException("Project not found with id: " + projectId));
            if (!employee.getProjects().contains(project)) {
                employee.addProject(project);
            }
        });
        Employee updatedEmployee = employeeRepository.save(employee);

        return new EmployeeDTO(
                updatedEmployee.getId(),
                updatedEmployee.getFirstName(),
                updatedEmployee.getLastName(),
                updatedEmployee.getEmail(),
                updatedEmployee.getSalary(),
                updatedEmployee.getDepartment().getId(),
                updatedEmployee.getAadhaar() != null ? updatedEmployee.getAadhaar().getId() : null,
                updatedEmployee.getProjects().stream().map(Project::getId).collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    public EmployeeDTO removeProjectsFromEmployee(Long employeeId, List<Long> projectIds) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeException("Employee not found with id: " + employeeId));

        projectIds.forEach(projectId -> {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ProjectException("Project not found with id: " + projectId));
            employee.removeProject(project); // Handles if project is not present
        });
        Employee updatedEmployee = employeeRepository.save(employee);

        return new EmployeeDTO(
                updatedEmployee.getId(),
                updatedEmployee.getFirstName(),
                updatedEmployee.getLastName(),
                updatedEmployee.getEmail(),
                updatedEmployee.getSalary(),
                updatedEmployee.getDepartment().getId(),
                updatedEmployee.getAadhaar() != null ? updatedEmployee.getAadhaar().getId() : null,
                updatedEmployee.getProjects().stream().map(Project::getId).collect(Collectors.toList())
        );
    }
}
