package ford.relationalMapping.CompanyManagementSystem.controller;

import ford.relationalMapping.CompanyManagementSystem.dto.EmployeeDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.EmployeeDetailDTO; // Changed to EmployeeDetailDTO
import ford.relationalMapping.CompanyManagementSystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDetailDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDetailDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{employeeId}/assign-aadhaar/{aadhaarId}")
    public ResponseEntity<EmployeeDTO> assignAadhaarToEmployee(@PathVariable Long employeeId, @PathVariable Long aadhaarId) {
        EmployeeDTO updatedEmployee = employeeService.assignAadhaarToEmployee(employeeId, aadhaarId);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PutMapping("/{employeeId}/assign-projects")
    public ResponseEntity<EmployeeDTO> assignProjectsToEmployee(@PathVariable Long employeeId, @RequestBody List<Long> projectIds) {
        EmployeeDTO updatedEmployee = employeeService.assignProjectsToEmployee(employeeId, projectIds);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PutMapping("/{employeeId}/remove-projects")
    public ResponseEntity<EmployeeDTO> removeProjectsFromEmployee(@PathVariable Long employeeId, @RequestBody List<Long> projectIds) {
        EmployeeDTO updatedEmployee = employeeService.removeProjectsFromEmployee(employeeId, projectIds);
        return ResponseEntity.ok(updatedEmployee);
    }
}
