package ford.relationalMapping.CompanyManagementSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
// dto : we can use this to get details of employee along with department id and also optionally aadhaar id and project ids
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "First name cannot be empty")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Salary cannot be null")
    @Min(value = 0, message = "Salary must be non-negative")
    private Double salary;

    @NotNull(message = "Department ID cannot be null")
    private Long departmentId;

    private Long aadhaarId; // Optional: Link existing Aadhaar
    private List<Long> projectIds; // Optional: Link to existing projects

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id, String firstName, String lastName, String email, Double salary, Long departmentId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public EmployeeDTO(Long id, String firstName, String lastName, String email, Double salary, Long departmentId, Long aadhaarId, List<Long> projectIds) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.departmentId = departmentId;
        this.aadhaarId = aadhaarId;
        this.projectIds = projectIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getAadhaarId() {
        return aadhaarId;
    }

    public void setAadhaarId(Long aadhaarId) {
        this.aadhaarId = aadhaarId;
    }

    public List<Long> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }
}
