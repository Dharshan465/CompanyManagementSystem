# Company Management System

This project implements a RESTful API for managing employees, departments, Aadhaar details, and projects within a company. It demonstrates various JPA relationships (One-to-Many, One-to-One, Many-to-Many), lazy loading, DTOs for efficient data transfer, and robust error handling using Spring Boot and Spring Data JPA.


## Features

*   **Entity Management**:
    *   **Department**: Details (name, location).
    *   **Employee**: Details (first name, last name, email, salary).
    *   **Aadhaar**: Unique identification details.
    *   **Project**: Details (name, budget).
*   **Relationships**:
    *   **Department <> Employee**: One-to-Many (Department has many Employees, Employee belongs to one Department).
    *   **Employee <> Aadhaar**: One-to-One (Employee has one Aadhaar, Aadhaar belongs to one Employee).
    *   **Employee <> Project**: Many-to-Many (Employee can work on many Projects, Project can have many Employees).
*   **Lazy Loading**: Configured for all relationships where appropriate (`FetchType.LAZY`) to optimize database queries.
*   **RESTful API**: Comprehensive API endpoints for CRUD operations.
*   **Data Transfer Objects (DTOs)**: Used for request/response shaping, preventing infinite recursion (`@JsonIgnore`), and tailored data views.
*   **Global Exception Handling**: Centralized error handling using `@ControllerAdvice` for consistent `ErrorResponse` objects.
*   **Data Validation**: Input validation using `jakarta.validation` annotations.
*   **Swagger UI**: Interactive API documentation and testing interface.
*   **In-Memory Database**: H2 database for easy setup and testing.
*   **Sample Data**: `CommandLineRunner` populates initial data for quick testing.


## Project Structure

The project follows a standard Spring Boot layered architecture:
```
CompanyManagementSystem 
    ├── CompanyManagementSystemApplication.java // Main Spring Boot application 
    ├── controller // REST API Endpoints 
    │ ├── AadhaarController.java 
    │ ├── DepartmentController.java 
    │ ├── EmployeeController.java 
    │ └── ProjectController.java 
    ├── dto // Data Transfer Objects 
    │ ├── AadhaarDTO.java 
    │ ├── DepartmentDTO.java 
    │ ├── DepartmentDetailDTO.java 
    │ ├── EmployeeBasicDTO.java 
    │ ├── EmployeeDTO.java 
    │ ├── EmployeeDetailDTO.java 
    │ ├── ProjectDTO.java 
    │ └── ProjectDetailDTO.java 
    ├── entity // JPA Entities 
    │ ├── Aadhaar.java 
    │ ├── Department.java 
    │ ├── Employee.java 
    │ └── Project.java 
    ├── exception // Custom Exceptions & Global Handler 
    │ ├── AadhaarException.java 
    │ ├── DepartmentException.java 
    │ ├── EmployeeException.java 
    │ ├── ErrorResponse.java 
    │ ├── GlobalExceptionHandler.java 
    │ └── ProjectException.java 
    ├── repository // Spring Data JPA Repositories 
    │ ├── AadhaarRepository.java 
    │ ├── DepartmentRepository.java 
    │ ├── EmployeeRepository.java 
    │ └── ProjectRepository.java 
    └── service // Business Logic (Service Interfaces & Implementations) 
    ├── AadhaarService.java 
    ├── DepartmentService.java 
    ├── EmployeeService.java 
    ├── ProjectService.java 
    ├── AadhaarServiceImplementation.java 
    ├── DepartmentServiceImplementation.java 
    ├── EmployeeServiceImplementation.java 
    └── ProjectServiceImplementation.java
```
### Key Implementation Details

- Lazy Loading: Configured for Department.employees, Employee.department, Employee.aadhaar, Aadhaar.employee, and Employee.projects. Project.employees is EAGERly loaded. Service methods use `@Transactional(readOnly = true)` to enable access to lazy-loaded entities within the transaction boundary.
- DTOs for JSON Serialization: Custom DTOs are used to control JSON output and prevent LazyInitializationException. `@JsonIgnore` is used on Employee.projects and Aadhaar.employee to break infinite recursion in bidirectional relationships.
- Bidirectional Relationship Management: Helper methods (e.g., addEmployee, addProject) in entities ensure both sides of a relationship are updated consistently.
- Orphan Removal: orphanRemoval = true on Department.employees and Employee.aadhaar automatically deletes associated child records if they are unlinked from their parent.
- `@Transactional`: Essential for defining transaction scope, ensuring operations (including lazy loading) complete atomically.
- Global Exception Handling: GlobalExceptionHandler provides consistent ErrorResponse for various exceptions (NotFound, Conflict, BadRequest).


## Github Link : 