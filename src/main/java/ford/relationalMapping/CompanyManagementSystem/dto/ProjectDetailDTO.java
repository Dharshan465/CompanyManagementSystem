package ford.relationalMapping.CompanyManagementSystem.dto;

import java.util.List;

public class ProjectDetailDTO {
    private Long id;
    private String name;
    private Double budget;
    private List<EmployeeBasicDTO> employees; // Using EmployeeBasicDTO

    public ProjectDetailDTO() {
    }

    public ProjectDetailDTO(Long id, String name, Double budget, List<EmployeeBasicDTO> employees) {
        this.id = id;
        this.name = name;
        this.budget = budget;
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

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public List<EmployeeBasicDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeBasicDTO> employees) {
        this.employees = employees;
    }
}
