package ford.relationalMapping.CompanyManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DepartmentException extends RuntimeException {
    public DepartmentException(String message) {
        super(message);
    }

    public DepartmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
