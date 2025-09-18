package ford.relationalMapping.CompanyManagementSystem.repository;

import ford.relationalMapping.CompanyManagementSystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
