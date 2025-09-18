package ford.relationalMapping.CompanyManagementSystem.dto;

import java.util.List;
// dto : we can use this to get details of employee along with department and aadhaar and projects
public class EmployeeDetailDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Double salary;
    private DepartmentDTO department;
    private AadhaarDTO aadhaar; // Now includes Aadhaar
    private List<ProjectDTO> projects; // Now includes Projects

    public EmployeeDetailDTO() {
    }

    public EmployeeDetailDTO(Long id, String firstName, String lastName, String email, Double salary, DepartmentDTO department, AadhaarDTO aadhaar, List<ProjectDTO> projects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.department = department;
        this.aadhaar = aadhaar;
        this.projects = projects;
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

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public AadhaarDTO getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(AadhaarDTO aadhaar) {
        this.aadhaar = aadhaar;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
