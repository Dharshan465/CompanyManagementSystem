package ford.relationalMapping.CompanyManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import ford.relationalMapping.CompanyManagementSystem.dto.AadhaarDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.DepartmentDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.DepartmentDetailDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.EmployeeDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.EmployeeDetailDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.ProjectDTO;
import ford.relationalMapping.CompanyManagementSystem.dto.ProjectDetailDTO;
import ford.relationalMapping.CompanyManagementSystem.service.AadhaarService;
import ford.relationalMapping.CompanyManagementSystem.service.DepartmentService;
import ford.relationalMapping.CompanyManagementSystem.service.EmployeeService;
import ford.relationalMapping.CompanyManagementSystem.service.ProjectService;
import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@SpringBootApplication
public class CompanyManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyManagementSystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(DepartmentService departmentService,
								 EmployeeService employeeService,
								 AadhaarService aadhaarService,
								 ProjectService projectService) {
		return args -> {
			System.out.println("Initializing sample data...");

			// 1. Create Departments
			DepartmentDTO hrDept = departmentService.createDepartment(new DepartmentDTO(null, "Human Resources", "Building A, Floor 1"));
			DepartmentDTO itDept = departmentService.createDepartment(new DepartmentDTO(null, "Information Technology", "Building B, Floor 3"));
			DepartmentDTO salesDept = departmentService.createDepartment(new DepartmentDTO(null, "Sales", "Building C, Floor 2"));

			System.out.println("Created Departments: HR ID " + hrDept.getId() + ", IT ID " + itDept.getId() + ", Sales ID " + salesDept.getId());

			// 2. Create Aadhaar Details
			AadhaarDTO aadhaar1 = aadhaarService.createAadhaar(new AadhaarDTO(null, "123456789012"));
			AadhaarDTO aadhaar2 = aadhaarService.createAadhaar(new AadhaarDTO(null, "234567890123"));
			AadhaarDTO aadhaar3 = aadhaarService.createAadhaar(new AadhaarDTO(null, "345678901234"));
			AadhaarDTO aadhaar4 = aadhaarService.createAadhaar(new AadhaarDTO(null, "456789012345"));
			AadhaarDTO aadhaar5 = aadhaarService.createAadhaar(new AadhaarDTO(null, "567890123456"));
			AadhaarDTO aadhaar6 = aadhaarService.createAadhaar(new AadhaarDTO(null, "678901234567"));

			System.out.println("Created Aadhaar: " + aadhaar1.getAadhaarNumber() + " (ID: " + aadhaar1.getId() + ")");
			System.out.println("Created Aadhaar: " + aadhaar2.getAadhaarNumber() + " (ID: " + aadhaar2.getId() + ")");
			System.out.println("Created Aadhaar: " + aadhaar3.getAadhaarNumber() + " (ID: " + aadhaar3.getId() + ")");

			// 3. Create Projects
			ProjectDTO projectA = projectService.createProject(new ProjectDTO(null, "Project Alpha", 150000.0));
			ProjectDTO projectB = projectService.createProject(new ProjectDTO(null, "Project Beta", 250000.0));
			ProjectDTO projectC = projectService.createProject(new ProjectDTO(null, "Project Gamma", 50000.0));

			System.out.println("Created Projects: Alpha ID " + projectA.getId() + ", Beta ID " + projectB.getId() + ", Gamma ID " + projectC.getId());

			// 4. Create Employees with initial Department, Aadhaar, and Projects
			EmployeeDTO emp1 = employeeService.createEmployee(new EmployeeDTO(null, "Alice", "Smith", "alice.smith@example.com", 70000.0, hrDept.getId(), aadhaar1.getId(), Arrays.asList(projectA.getId())));
			EmployeeDTO emp2 = employeeService.createEmployee(new EmployeeDTO(null, "Bob", "Johnson", "bob.j@example.com", 85000.0, itDept.getId(), aadhaar2.getId(), Arrays.asList(projectA.getId(), projectB.getId())));
			EmployeeDTO emp3 = employeeService.createEmployee(new EmployeeDTO(null, "Charlie", "Brown", "charlie.b@example.com", 60000.0, salesDept.getId(), aadhaar3.getId(), Collections.singletonList(projectC.getId())));
			EmployeeDTO emp4 = employeeService.createEmployee(new EmployeeDTO(null, "Diana", "Prince", "diana.p@example.com", 95000.0, itDept.getId(), aadhaar4.getId(), Arrays.asList(projectA.getId(), projectB.getId(), projectC.getId())));
			EmployeeDTO emp5 = employeeService.createEmployee(new EmployeeDTO(null, "Eve", "Adams", "eve.a@example.com", 72000.0, hrDept.getId(), null, Collections.emptyList())); // Eve has no Aadhaar or projects initially

			System.out.println("Created Employees: Alice ID " + emp1.getId() + ", Bob ID " + emp2.getId() + ", Charlie ID " + emp3.getId() + ", Diana ID " + emp4.getId() + ", Eve ID " + emp5.getId());

			// 5. Demonstrate assigning Aadhaar to an existing employee
			System.out.println("\n--- Assigning Aadhaar to Eve ---");
			employeeService.assignAadhaarToEmployee(emp5.getId(), aadhaar5.getId());
			System.out.println("Eve (ID: " + emp5.getId() + ") now has Aadhaar " + aadhaar5.getAadhaarNumber());

			// 6. Demonstrate assigning Project to an existing employee
			System.out.println("\n--- Assigning Project C to Alice ---");
			employeeService.assignProjectsToEmployee(emp1.getId(), Collections.singletonList(projectC.getId()));
			System.out.println("Alice (ID: " + emp1.getId() + ") now works on Project C.");

			// 7. Demonstrate removing Project from an existing employee
			System.out.println("\n--- Removing Project A from Charlie ---");
			employeeService.removeProjectsFromEmployee(emp3.getId(), Collections.singletonList(projectA.getId())); // Charlie was only on Project C, this will show no change
			System.out.println("Charlie (ID: " + emp3.getId() + ") removed from Project A (if he was on it)."); // Corrected: Charlie was on project C, this will not affect him. Let's add him to A first.

			// Corrected: Add Charlie to Project A, then remove
			System.out.println("\n--- Adding Charlie to Project A, then removing ---");
			employeeService.assignProjectsToEmployee(emp3.getId(), Collections.singletonList(projectA.getId()));
			EmployeeDetailDTO charlieWithProjectA = employeeService.getEmployeeById(emp3.getId());
			System.out.println("Charlie's Projects after adding A: " + charlieWithProjectA.getProjects().stream().map(ProjectDTO::getName).collect(Collectors.toList()));
			employeeService.removeProjectsFromEmployee(emp3.getId(), Collections.singletonList(projectA.getId()));
			EmployeeDetailDTO charlieAfterRemoval = employeeService.getEmployeeById(emp3.getId());
			System.out.println("Charlie's Projects after removing A: " + charlieAfterRemoval.getProjects().stream().map(ProjectDTO::getName).collect(Collectors.toList()));


			System.out.println("\n--- Lazy Loading Demonstrations ---");

			// 8. Fetch department without employees (lazy fetching)
			System.out.println("\n--- Listing all departments (without employee details) ---");
			departmentService.getAllDepartments().forEach(dept ->
					System.out.println("Department ID: " + dept.getId() + ", Name: " + dept.getName() + ", Location: " + dept.getLocation())
			);

			// 9. Fetch department with employees (trigger lazy loading)
			System.out.println("\n--- Getting IT Department details with its employees ---");
			DepartmentDetailDTO itDepartmentDetails = departmentService.getDepartmentById(itDept.getId());
			System.out.println("Department: " + itDepartmentDetails.getName() + " (ID: " + itDepartmentDetails.getId() + ")");
			itDepartmentDetails.getEmployees().forEach(e ->
					System.out.println("  Employee: " + e.getFirstName() + " " + e.getLastName() + " (Email: " + e.getEmail() + ")")
			);

			// 10. Fetch employee with department, aadhaar, and project info (trigger lazy loading for Department, Aadhaar, Projects)
			System.out.println("\n--- Getting Bob Johnson's full details ---");
			EmployeeDetailDTO bobDetails = employeeService.getEmployeeById(emp2.getId());
			System.out.println("Employee: " + bobDetails.getFirstName() + " " + bobDetails.getLastName() + " (Email: " + bobDetails.getEmail() + ")");
			System.out.println("  Department: " + bobDetails.getDepartment().getName());
			System.out.println("  Aadhaar: " + (bobDetails.getAadhaar() != null ? bobDetails.getAadhaar().getAadhaarNumber() : "N/A"));
			System.out.println("  Projects: " + (bobDetails.getProjects().isEmpty() ? "None" : bobDetails.getProjects().stream().map(ProjectDTO::getName).collect(Collectors.joining(", "))));

			// 11. Fetch project with its employees (EAGER ly loaded employees on Project side)
			System.out.println("\n--- Getting Project Alpha details with its employees ---");
			ProjectDetailDTO projectAlphaDetails = projectService.getProjectById(projectA.getId());
			System.out.println("Project: " + projectAlphaDetails.getName() + " (Budget: $" + projectAlphaDetails.getBudget() + ")");
			projectAlphaDetails.getEmployees().forEach(e ->
					System.out.println("  Employee: " + e.getFirstName() + " " + e.getLastName() + " (Email: " + e.getEmail() + ")")
			);

			System.out.println("\nSample data initialization and demonstration complete.");
		};
	}
}
