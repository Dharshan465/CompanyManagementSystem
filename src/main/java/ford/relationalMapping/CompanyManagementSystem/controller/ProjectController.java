package ford.relationalMapping.CompanyManagementSystem.controller;

import ford.relationalMapping.CompanyManagementSystem.dto.ProjectDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.ProjectDetailDTO;
import ford.relationalMapping.CompanyManagementSystem.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        ProjectDTO createdProject = projectService.createProject(projectDTO);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailDTO> getProjectById(@PathVariable Long id) {
        ProjectDetailDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/{projectId}/add-employee/{employeeId}")
    public ResponseEntity<ProjectDetailDTO> addEmployeeToProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        ProjectDetailDTO updatedProject = projectService.addEmployeeToProject(projectId, employeeId);
        return ResponseEntity.ok(updatedProject);
    }

    @PutMapping("/{projectId}/remove-employee/{employeeId}")
    public ResponseEntity<ProjectDetailDTO> removeEmployeeFromProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        ProjectDetailDTO updatedProject = projectService.removeEmployeeFromProject(projectId, employeeId);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
