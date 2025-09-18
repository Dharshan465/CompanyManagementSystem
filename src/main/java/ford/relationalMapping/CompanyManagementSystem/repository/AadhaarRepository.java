package ford.relationalMapping.CompanyManagementSystem.repository;

import ford.relationalMapping.CompanyManagementSystem.entity.Aadhaar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AadhaarRepository extends JpaRepository<Aadhaar, Long> {
    Optional<Aadhaar> findByAadhaarNumber(String aadhaarNumber);
}
