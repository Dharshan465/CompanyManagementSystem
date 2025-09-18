package ford.relationalMapping.CompanyManagementSystem.repository;

import ford.relationalMapping.CompanyManagementSystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
