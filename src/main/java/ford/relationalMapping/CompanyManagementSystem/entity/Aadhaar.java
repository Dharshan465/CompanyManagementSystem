package ford.relationalMapping.CompanyManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "aadhaar_details")
public class Aadhaar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Aadhaar number cannot be empty")
    @Size(min = 12, max = 12, message = "Aadhaar number must be 12 digits long")
    @Pattern(regexp = "\\d{12}", message = "Aadhaar number must contain only digits")
    @Column(nullable = false, unique = true, length = 12)
    private String aadhaarNumber;

    // Bidirectional mapping
    // mappedBy will avoid creation of foreign key in Aadhaar table
    @OneToOne(mappedBy = "aadhaar", fetch = FetchType.LAZY)
    // @JsonIgnore to prevent infinite recursion during JSON serialization
    @JsonIgnore
    private Employee employee;

    public Aadhaar() {
    }

    public Aadhaar(String aadhaarNumber) {
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
