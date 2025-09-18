package ford.relationalMapping.CompanyManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// DTO : We can use this get the id and aadhaar number from the request body
public class AadhaarDTO {
    private Long id;

    @NotBlank(message = "Aadhaar number cannot be empty")
    @Size(min = 12, max = 12, message = "Aadhaar number must be 12 digits long")
    @Pattern(regexp = "\\d{12}", message = "Aadhaar number must contain only digits")
    private String aadhaarNumber;

    public AadhaarDTO() {
    }

    public AadhaarDTO(Long id, String aadhaarNumber) {
        this.id = id;
        this.aadhaarNumber = aadhaarNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }
}
