package ford.relationalMapping.CompanyManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name cannot be empty")
    @Size(max = 100, message = "Project name cannot exceed 100 characters")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "Budget cannot be null")
    @Min(value = 0, message = "Budget must be non-negative")
    @Column(nullable = false)
    private Double budget;

    // Many-to-Many relationship with Employee
    // Project is the owning side, defines the join table
    @ManyToMany(fetch = FetchType.EAGER) // As requested, EAGER fetching for projects' employees
    @JoinTable(name = "project_employee",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id")
    )
    private List<Employee> employees = new ArrayList<>();

    public Project() {
    }

    public Project(String name, Double budget) {
        this.name = name;
        this.budget = budget;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    // Helper methods for ManyToMany
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getProjects().add(this);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getProjects().remove(this);
    }
}
