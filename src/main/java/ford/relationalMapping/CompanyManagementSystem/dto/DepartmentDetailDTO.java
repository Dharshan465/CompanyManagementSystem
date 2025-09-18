package ford.relationalMapping.CompanyManagementSystem.dto;

import java.util.List;
// Dto : we can use this to get the details of department along with its employees
public class DepartmentDetailDTO {
    private Long id;
    private String name;
    private String location;
    private List<EmployeeBasicDTO> employees; // Using EmployeeBasicDTO

    public DepartmentDetailDTO() {
    }

    public DepartmentDetailDTO(Long id, String name, String location, List<EmployeeBasicDTO> employees) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<EmployeeBasicDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeBasicDTO> employees) {
        this.employees = employees;
    }
}
