package ford.relationalMapping.CompanyManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
// Dto :we can use this to get details of department alone.
public class DepartmentDTO {
    private Long id;

    @NotBlank(message = "Department name cannot be empty")
    @Size(max = 100, message = "Department name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Location cannot be empty")
    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    public DepartmentDTO() {
    }

    public DepartmentDTO(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
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
}
