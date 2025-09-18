package ford.relationalMapping.CompanyManagementSystem.service;

import ford.relationalMapping.CompanyManagementSystem.dto.ProjectDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.ProjectDetailDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO);
    ProjectDetailDTO getProjectById(Long id);
    List<ProjectDTO> getAllProjects();
    ProjectDetailDTO addEmployeeToProject(Long projectId, Long employeeId);
    ProjectDetailDTO removeEmployeeFromProject(Long projectId, Long employeeId);
    void deleteProject(Long id);
}
