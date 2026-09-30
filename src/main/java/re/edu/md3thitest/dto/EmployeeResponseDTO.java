package re.edu.md3thitest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import re.edu.md3thitest.entity.Employee;
import re.edu.md3thitest.entity.StatusEmployee;
import re.edu.md3thitest.entity.TypeDepartment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeResponseDTO {
    private Long id;
    private String employeeCode; // Mã nhân viên
    private String fullName;
    private String email;
    private String phone;
    private TypeDepartment department;
    private String position;
    private BigDecimal salary;
    private LocalDate hireDate;
    private StatusEmployee status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EmployeeResponseDTO(Employee employee) {
        this.id = employee.getId();
        this.employeeCode = employee.getEmployeeCode();
        this.fullName = employee.getFullName();
        this.email = employee.getEmail();
        this.phone = employee.getPhone();
        this.department = employee.getDepartment();
        this.position = employee.getPosition();
        this.salary = employee.getSalary();
        this.hireDate = employee.getHireDate();
        this.status = employee.getStatus();
        this.createdAt = employee.getCreatedAt();
        this.updatedAt = employee.getUpdatedAt();
    }
}
