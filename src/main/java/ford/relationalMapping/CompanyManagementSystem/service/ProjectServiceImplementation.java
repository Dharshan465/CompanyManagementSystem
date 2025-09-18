package ford.relationalMapping.CompanyManagementSystem.service;

import ford.relationalMapping.CompanyManagementSystem.dto.EmployeeBasicDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.ProjectDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.ProjectDetailDTO;
import ford.relationalMapping.CompanyManagementSystem.entity.Employee;
import ford.relationalMapping.CompanyManagementSystem.entity.Project;
import ford.relationalMapping.CompanyManagementSystem.exception.EmployeeException;
import ford.relationalMapping.CompanyManagementSystem.exception.ProjectException;
import ford.relationalMapping.CompanyManagementSystem.repository.EmployeeRepository;
import ford.relationalMapping.CompanyManagementSystem.repository.ProjectRepository;
import ford.relationalMapping.CompanyManagementSystem.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImplementation implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    public ProjectServiceImplementation(ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setBudget(projectDTO.getBudget());
        Project savedProject = projectRepository.save(project);
        return new ProjectDTO(savedProject.getId(), savedProject.getName(), savedProject.getBudget());
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDetailDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectException("Project not found with id: " + id));

        // Employees are EAGERly fetched on the Project side, so no explicit .get() needed to trigger
        List<EmployeeBasicDTO> employees = project.getEmployees().stream()
                .map(employee -> new EmployeeBasicDTO(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail()))
                .collect(Collectors.toList());

        return new ProjectDetailDTO(project.getId(), project.getName(), project.getBudget(), employees);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(project -> new ProjectDTO(project.getId(), project.getName(), project.getBudget()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectDetailDTO addEmployeeToProject(Long projectId, Long employeeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException("Project not found with id: " + projectId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeException("Employee not found with id: " + employeeId));

        if (!project.getEmployees().contains(employee)) {
            project.addEmployee(employee);
            projectRepository.save(project);
        }
        // Re-fetch to ensure the latest state with EAGERly loaded employees
        return getProjectById(projectId);
    }

    @Override
    @Transactional
    public ProjectDetailDTO removeEmployeeFromProject(Long projectId, Long employeeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException("Project not found with id: " + projectId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeException("Employee not found with id: " + employeeId));

        project.removeEmployee(employee);
        projectRepository.save(project);
        // Re-fetch to ensure the latest state with EAGERly loaded employees
        return getProjectById(projectId);
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ProjectException("Project not found with id: " + id);
        }
        // When deleting a project, the entries in the join table are removed automatically.
        // Employees are not deleted.
        projectRepository.deleteById(id);
    }
}
