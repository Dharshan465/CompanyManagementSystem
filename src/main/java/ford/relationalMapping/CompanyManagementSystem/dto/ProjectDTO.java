package ford.relationalMapping.CompanyManagementSystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProjectDTO {
    private Long id;

    @NotBlank(message = "Project name cannot be empty")
    @Size(max = 100, message = "Project name cannot exceed 100 characters")
    private String name;

    @NotNull(message = "Budget cannot be null")
    @Min(value = 0, message = "Budget must be non-negative")
    private Double budget;

    public ProjectDTO() {
    }

    public ProjectDTO(Long id, String name, Double budget) {
        this.id = id;
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
}
